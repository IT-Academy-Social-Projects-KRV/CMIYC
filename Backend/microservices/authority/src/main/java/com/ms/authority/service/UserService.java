package com.ms.authority.service;

import com.ms.authority.dto.ConfirmRegisterData;
import com.ms.authority.dto.RegistrationRequestData;
import com.ms.authority.dto.RegistrationResultData;
import com.ms.authority.dto.UserData;
import com.ms.authority.entity.Role;
import com.ms.authority.entity.Token;
import com.ms.authority.entity.User;
import com.ms.authority.exception.ImpossibleOperationException;
import com.ms.authority.exception.PasswordsDoNotMatchException;
import com.ms.authority.exception.TokenNotFoundException;
import com.ms.authority.exception.UserAlreadyRegistredException;
import com.ms.authority.exception.UserNotFoundException;
import com.ms.authority.repository.RoleRepository;
import com.ms.authority.repository.UserRepository;
import dev.samstevens.totp.exceptions.QrGenerationException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;

import java.util.Comparator;
import java.util.List;
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

    @Value("${routes.ui.activation-page}")
    private String activationPage;

    @Override
    public User loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, email)));
    }

    public RegistrationResultData signUpUser(User user, Token token) {
        boolean userExist = userRepository.findByEmail(user.getEmail())
                .isPresent();
        if (userExist) {
            // TODO check of attributes are the same and
            // TODO if email not confirmed send confirmation email
            return new RegistrationResultData(true, "email already taken");
        }
        String encodePassword = bCryptPasswordEncoder.encode(DEFAULT_PASSWORD);
        user.setPassword(encodePassword);

        userRepository.save(user);
        token.setUser(user);
        tokenService.saveVerificationToken(token);

        return new RegistrationResultData(false, "All it is okay");
    }

    public RegistrationResultData register(RegistrationRequestData request) {
        Token token = new Token();

        String link = activationPage + token.getToken();
        String secret = tfaService.generateSecretKey();
        String qrCode = null;
        try {
            qrCode = tfaService.getQRCode(request.getEmail(), secret);
        } catch (QrGenerationException e) {
            e.printStackTrace();
        }
        try {
            emailService.sendActivationLink(request.getEmail(), request.getFirstName(), link, qrCode);
        } catch (MessagingException e) {
            return new RegistrationResultData(true, "Email is invalid");
        }
        // TODO registration result (boolean)
        Set<Role> roleSet = request.getRoles()
                .stream()
                .map(roleRepository::findByRole)
                .collect(Collectors.toSet());

        return signUpUser(new User(request.getFirstName(), request.getLastName(), request.getEmail(), secret, roleSet), token);
    }

    public void confirmRegister(ConfirmRegisterData confirmRegisterData)
            throws PasswordsDoNotMatchException, TokenNotFoundException, UserAlreadyRegistredException {
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

    public List<UserData> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserData::convertToUserData)
                .sorted(Comparator.comparing(UserData::getId))
                .collect(Collectors.toList());
    }

}
