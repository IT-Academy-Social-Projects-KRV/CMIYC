package com.ms.authority.service;

import com.ms.authority.email.EmailService;

import java.util.Set;
import java.util.stream.Collectors;

import com.ms.authority.dto.UserDto;
import com.ms.authority.entity.Role;
import com.ms.authority.entity.User;
import com.ms.authority.entity.Token;
import com.ms.authority.repository.UserRepository;
import com.ms.authority.dto.RegistrationRequest;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final static String USER_NOT_FOUND_MSG = "User with email %s not found";
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final TokenService tokenService;
    private final EmailService emailService;


    @Override
    public User loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, email)));
    }

    public String signUpUser(User user, Token token) {
        boolean userExist = userRepository
                .findByEmail(user.getEmail())
                .isPresent();
        if (userExist) {
            //TODO check of attributes are the same and
            //TODO if email not confirmed send confirmation email
            throw new IllegalStateException("email already taken");
        }
        String encodePassword = bCryptPasswordEncoder.encode("user.getPassword()");
        user.setPassword(encodePassword);
        userRepository.save(user);

        token.setUser(user);

        tokenService.saveVerificationToken(token);
        //TODO SEND EMAIL
        return "ok";
    }


    public String register(RegistrationRequest request) {
        Token token = new Token();

        String link = "http://localhost:8080/users/registration/confirm?token=" + token.getToken();
        try {
            emailService.sendActivationLink(
                    request.getEmail(),
                    request.getFirstName(),
                    link);
        } catch (MessagingException e) {
            return "error";
        }
        //TODO registration result (boolean)

        return signUpUser(
                new User(
                        request.getFirstName(),
                        request.getLastName(),
                        request.getEmail()
                        //  request.getRole()
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
