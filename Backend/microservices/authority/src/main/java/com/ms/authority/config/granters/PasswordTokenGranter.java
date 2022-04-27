package com.ms.authority.config.granters;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.TokenRequest;
import org.springframework.security.oauth2.provider.token.AbstractTokenGranter;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.ms.authority.utils.Authorities.Messages.COULD_NOT_AUTHENTICATE_USER_MSG;
import static com.ms.authority.utils.Authorities.PASSWORD_GRANT_TYPE;
import static com.ms.authority.utils.Authorities.PRE_AUTH;

public class PasswordTokenGranter extends AbstractTokenGranter {

    private final AuthenticationManager authenticationManager;

    public PasswordTokenGranter(
            AuthorizationServerEndpointsConfigurer endpointsConfigurer, AuthenticationManager authenticationManager
    ) {
        super(
                endpointsConfigurer.getTokenServices(),
                endpointsConfigurer.getClientDetailsService(),
                endpointsConfigurer.getOAuth2RequestFactory(),
                PASSWORD_GRANT_TYPE
        );
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected OAuth2Authentication getOAuth2Authentication(ClientDetails client, TokenRequest tokenRequest) {
        Map<String, String> parameters = new LinkedHashMap<>(tokenRequest.getRequestParameters());

        String username = parameters.get("username");
        String password = parameters.get("password");
        parameters.remove("password");

        Authentication userAuth = new UsernamePasswordAuthenticationToken(username, password);
        ((AbstractAuthenticationToken) userAuth).setDetails(parameters);

        try {
            userAuth = this.authenticationManager.authenticate(userAuth);
        } catch (AccountStatusException | BadCredentialsException e) {
            throw new InvalidGrantException(e.getMessage());
        }

        if (userAuth == null || !userAuth.isAuthenticated()) {
            throw new InvalidGrantException(COULD_NOT_AUTHENTICATE_USER_MSG + username);
        }

        OAuth2Request storedOAuth2Request = this.getRequestFactory().createOAuth2Request(client, tokenRequest);
        userAuth = new UsernamePasswordAuthenticationToken(username, password, Collections.singleton(PRE_AUTH));

        return new OAuth2Authentication(storedOAuth2Request, userAuth);
    }

}