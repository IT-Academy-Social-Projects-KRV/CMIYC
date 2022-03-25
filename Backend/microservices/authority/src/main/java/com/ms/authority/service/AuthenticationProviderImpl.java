package com.ms.authority.service;

import com.ms.authority.entity.User;
import com.ms.authority.utils.FrontendData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * This bean is responsible for checking whether or not entered email and password was correct
 */
@Service
public class AuthenticationProviderImpl implements AuthenticationProvider {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * This method called whenever user send his email and password via login form
     * We need to check if this email and pass is correct
     *
     * @param authentication Contains entered email and password
     * @return new Authentication object if email and password are correct
     * @throws AuthenticationException if email or password wrong
     * @see Authentication
     * @see UserService
     * @see AuthenticationProvider#authenticate(Authentication)
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // Extract email from authentication
        String email = authentication.getName();

        // Extract password from authentication
        String password = authentication.getCredentials().toString();

        // Load user from database. Will throw exception if no such user exists
        User user = userService.loadUserByUsername(email);

        // Extract hashed password
        String hashedPass = user.getPassword();

        // If hash of entered password is not equal to hashed password, throw an exception
        if (!passwordEncoder.matches(password, hashedPass))
            throw new BadCredentialsException("Wrong password!");

        // Return new authentication object
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                user.getUsername(),
                hashedPass,
                user.getRoles()
        );

        // Adding front data to authentication
        token.setDetails(new FrontendData(
                user.getEmail(),
                user.getFirstName() + " " + user.getLastName()
        ));

        return token;
    }

    /**
     * Read parent documentation:
     *
     * @see AuthenticationProvider#supports(Class)
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
