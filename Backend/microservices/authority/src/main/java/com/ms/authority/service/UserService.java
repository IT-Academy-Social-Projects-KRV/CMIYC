package com.ms.authority.service;

import com.ms.authority.dto.ConfirmRegisterData;
import com.ms.authority.dto.RegistrationRequestData;
import com.ms.authority.dto.RegistrationResultData;
import com.ms.authority.dto.UserData;
import com.ms.authority.entity.Role;
import com.ms.authority.entity.Token;
import com.ms.authority.entity.User;
import com.ms.authority.exception.BadRegisterDataException;
import com.ms.authority.exception.ImpossibleOperationException;
import com.ms.authority.exception.NotEnoughRolesSelectedException;
import com.ms.authority.exception.PasswordsDoNotMatchException;
import com.ms.authority.exception.TokenNotFoundException;
import com.ms.authority.exception.UserAlreadyRegistredException;
import com.ms.authority.exception.UserNotFoundException;
import com.ms.authority.repository.RoleRepository;
import com.ms.authority.repository.UserRepository;
import dev.samstevens.totp.exceptions.QrGenerationException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional
public class UserService implements UserDetailsService {

    private static final String USER_NOT_FOUND_MSG = "User with email %s not found";
    private static final String USER_WITH_ID_NOT_FOUND_MSG = "User with ID %s not found";
    private static final String CANNOT_DISABLE_ADMIN_MSG = "The \"active\" field cannot be changed for an administrator with the \"User Manager\" role.";
    private static final String CANNOT_CHANGE_ACTIVE_UNREGISTRED_USER_MSG = "The \"active\" field cannot be changed for an unregistered user";
    private static final String DEFAULT_PASSWORD = "mBpQAW8mZY235LCCy9HhcYj24ELyK25zeG9v4Sg";

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final TokenService tokenService;
    private final EmailService emailService;
    private final RoleRepository roleRepository;
    private final TfaService tfaService;
    private final RecaptchaService recaptchaService;

    @Value("${routes.ui.activation-page}")
    private String activationPage;

    @PersistenceContext
    private EntityManager em;

    @Override
    public User loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, email)));
    }

    public Page<UserData> findUserByParams(String email, String firstName, String lastName, Boolean isActive,
                                           int page, int size) {
        return userRepository.findUserByParams(email, firstName, lastName, isActive, PageRequest.of(page, size, Sort.by("firstName")))
                .map(UserData::convertToUserData);
    }

    public boolean isUserExist(String email){
        return userRepository.existsByEmail(email);
    }

    @SneakyThrows
    public synchronized void register(RegistrationRequestData request) {
        request.validate();

        if(isUserExist(request.getEmail()))
            throw new UserAlreadyRegistredException();

        Token token = new Token();
        String link = activationPage + token.getToken();
        String secret = tfaService.generateSecretKey();
        String qrCode = tfaService.getQRCode(request.getEmail(), secret);
        emailService.sendActivationLink(request.getEmail(), request.getFirstName(), link, qrCode);

        Set<Role> roleSet = extractRolesFromStrings(request.getRoles());
        User user = new User(request.getFirstName(), request.getLastName(), request.getEmail(), secret, roleSet);

        String encodePassword = bCryptPasswordEncoder.encode(DEFAULT_PASSWORD);
        user.setPassword(encodePassword);
        userRepository.save(user);

        token.setUser(user);
        tokenService.saveVerificationToken(token);
    }

    public void confirmRegister(ConfirmRegisterData confirmRegisterData, String ip)
            throws PasswordsDoNotMatchException, TokenNotFoundException, UserAlreadyRegistredException {
        recaptchaService.checkResponse(confirmRegisterData.getCaptchaResponse(), ip);

        if (!confirmRegisterData.arePasswordsEquals()) {
            throw new PasswordsDoNotMatchException();
        }

        User user = tokenService.getToken(confirmRegisterData.getToken())
                .orElseThrow(TokenNotFoundException::new)
                .getUser();

        if (user.isEnabled()) {
            throw new UserAlreadyRegistredException();
        }

        user.setPassword(bCryptPasswordEncoder.encode(confirmRegisterData.getPassword()));
        user.setActive(true);
        userRepository.save(user);
    }

    public void changeUserActive(int userId, boolean isActive)
            throws UserNotFoundException, ImpossibleOperationException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(String.format(USER_WITH_ID_NOT_FOUND_MSG, userId)));

        if (user.isUserAdmin()) {
            throw new ImpossibleOperationException(CANNOT_DISABLE_ADMIN_MSG);
        }

        if (isActive && bCryptPasswordEncoder.matches(DEFAULT_PASSWORD, user.getPassword())) {
            throw new ImpossibleOperationException(CANNOT_CHANGE_ACTIVE_UNREGISTRED_USER_MSG);
        }

        user.setActive(isActive);

        userRepository.save(user);
    }

    public Page<UserData> getAllUsers(int page, int size) {
        return userRepository.findAll(PageRequest.of(page, size, Sort.by("firstName")))
                .map(UserData::convertToUserData);
    }

    public void deleteUser(User user) {
        if(user.getRoles().stream().anyMatch((role)-> role.getRole() .equals(Role.ROLE_ADMIN_USER)) ){
            throw new ImpossibleOperationException("Admin user can't delete himself or another admin users");
        }
        tokenService.deleteTokenByUser(user);
        userRepository.delete(user);
    }

    public User updateUserById(User user, RegistrationRequestData request) {
        if(request.getFirstName() == null)
            request.setFirstName(user.getFirstName());

        if(request.getLastName() == null)
            request.setLastName(user.getLastName());

        if(request.getEmail() == null)
            request.setEmail(user.getEmail());

        request.validate();

        userRepository.findById(user.getId()).map(userUpdated -> {
            user.setFirstName(request.getFirstName());
            user.setLastName(request.getLastName());
            user.setEmail(request.getEmail());
            user.setRoles(extractRolesFromStrings(request.getRoles()));

            return userRepository.save(user);
        });

        return user;
    }

    private Set<Role> extractRolesFromStrings(Set<String> strings) {
        return strings.stream()
                .map(roleRepository::findByRole)
                .collect(Collectors.toSet());
    }

}
