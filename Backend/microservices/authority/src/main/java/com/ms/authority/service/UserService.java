package com.ms.authority.service;

import com.ms.authority.dto.RegistrationResult;
import com.ms.authority.email.EmailService;
import java.util.Set;
import java.util.stream.Collectors;
import com.ms.authority.dto.UserDto;
import com.ms.authority.entity.Role;
import com.ms.authority.entity.User;
import com.ms.authority.entity.Token;
import com.ms.authority.repository.RoleRepository;
import com.ms.authority.repository.UserRepository;
import com.ms.authority.dto.RegistrationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import javax.mail.MessagingException;

@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {

    private final static String USER_NOT_FOUND_MSG = "User with email %s not found";

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final TokenService tokenService;
    private final EmailService emailService;
    private final RoleRepository roleRepository;

    @Value("${routes.ui.activation-page")
    private String activationPage;

    @Override
    public User loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, email)));
    }

    public RegistrationResult signUpUser(User user, Token token) {
        boolean userExist = userRepository
                .findByEmail(user.getEmail())
                .isPresent();
        if (userExist) {
            //TODO check of attributes are the same and
            //TODO if email not confirmed send confirmation email
            return new RegistrationResult(true, "email already taken");
        }
        String encodePassword = bCryptPasswordEncoder.encode("user.getPassword()");
        user.setPassword(encodePassword);


        userRepository.save(user);
        token.setUser(user);
        tokenService.saveVerificationToken(token);

        return new RegistrationResult(false, "All it is okay");
    }

    public RegistrationResult register(RegistrationRequest request) {
        Token token = new Token();

        String link = activationPage + token.getToken();
        try {
            emailService.sendActivationLink(
                    request.getEmail(),
                    request.getFirstName(),
                    link);
        } catch (MessagingException e) {
            return new RegistrationResult(true, "Email is invalid");
        }
        //TODO registration result (boolean)
        Set<Role> roleSet = request.getRoles().stream().map(roleRepository::findByRole).collect(Collectors.toSet());

        return signUpUser(
                new User(
                        request.getFirstName(),
                        request.getLastName(),
                        request.getEmail(),
                        roleSet
                ), token
        );
    }

    public User changeUserActive(int userId, boolean isActive) throws RuntimeException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("Unable to find user with this id"));
        if (user.isUserAdmin()) {
            throw new RuntimeException("The field \"active\" can't be change for user with role admin_user");
        }
        user.setActive(isActive);
        return userRepository.save(user);
    }

    public Set<UserDto> listUsersRequest() {
        return userRepository.findAll()
                .stream()
                .map(this::convertToUserDto)
                .collect(Collectors.toSet());
    }

    private UserDto convertToUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setActive(user.isEnabled());
        userDto.setRegisterDate(user.getRegisterDate());
        userDto.setScopes(user.getRoles().stream()
                .map(Role::getAuthority)
                .collect(Collectors.toSet()));
        return userDto;
    }
}
