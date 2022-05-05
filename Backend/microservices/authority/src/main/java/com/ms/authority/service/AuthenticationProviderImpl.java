package com.ms.authority.service;

import com.ms.authority.dto.FrontendData;
import com.ms.authority.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import static com.ms.authority.utils.Authorities.PRE_AUTH;

@Service
@AllArgsConstructor
public class AuthenticationProviderImpl implements AuthenticationProvider {

    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;

    /**
     * This method called whenever user send his email and password via login form
     * We need to check if this email and pass is correct
     *
     * @param authentication Contains entered email and password
     *
     * @return new Authentication object if email and password are correct
     *
     * @throws AuthenticationException if email or password wrong
     * @see Authentication
     * @see UserService
     * @see AuthenticationProvider#authenticate(Authentication)
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getName();
        String password = authentication.getCredentials().toString();
        User user = userService.loadUserByUsername(email);
        String hashedPass = user.getPassword();
        boolean isPreAuth = authentication.getAuthorities().contains(PRE_AUTH);

        if (!isPreAuth && !passwordEncoder.matches(password, hashedPass)) {
            throw new BadCredentialsException("Wrong password!");
        }

        if (!user.isActive()) {
            throw new BadCredentialsException("Your account is not active!");
        }

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                user.getUsername(),
                hashedPass,
                user.getRoles()
        );

        token.setDetails(new FrontendData(
                user.getEmail(),
                user.getFirstName() + " " + user.getLastName()
        ));

        return token;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }

}
