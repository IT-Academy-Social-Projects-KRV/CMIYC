package com.ms.authority.config.granters;

import com.ms.authority.service.TfaService;
import com.ms.authority.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.common.exceptions.InvalidRequestException;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.TokenRequest;
import org.springframework.security.oauth2.provider.token.AbstractTokenGranter;
import org.springframework.security.oauth2.provider.token.TokenStore;

import java.util.LinkedHashMap;
import java.util.Map;

import static com.ms.authority.utils.Authorities.Messages.ACCESS_TOKEN_EXPIRED_MSG;
import static com.ms.authority.utils.Authorities.Messages.INVALID_ACCESS_TOKEN_MSG;
import static com.ms.authority.utils.Authorities.Messages.INVALID_CLIENT_MSG;
import static com.ms.authority.utils.Authorities.Messages.INVALID_TFA_CODE_MSG;
import static com.ms.authority.utils.Authorities.Messages.MISSING_CLIENT_MSG;
import static com.ms.authority.utils.Authorities.Messages.MISSING_TFA_CODE_MSG;
import static com.ms.authority.utils.Authorities.Messages.MISSING_TFA_TOKEN_MSG;
import static com.ms.authority.utils.Authorities.TFA_GRANT_TYPE;

public class TfaTokenGranter extends AbstractTokenGranter {

    private final TokenStore tokenStore;
    private final ClientDetailsService clientDetailsService;
    private final AuthenticationManager authenticationManager;
    private final TfaService tfaService;
    private final UserService userService;

    public TfaTokenGranter(
        AuthorizationServerEndpointsConfigurer endpointsConfigurer,
        AuthenticationManager authenticationManager,
        TfaService tfaService,
        UserService userService
    ) {
        super(
            endpointsConfigurer.getTokenServices(),
            endpointsConfigurer.getClientDetailsService(),
            endpointsConfigurer.getOAuth2RequestFactory(),
            TFA_GRANT_TYPE
        );
        this.tokenStore = endpointsConfigurer.getTokenStore();
        this.clientDetailsService = endpointsConfigurer.getClientDetailsService();
        this.authenticationManager = authenticationManager;
        this.tfaService = tfaService;
        this.userService = userService;
    }

    @Override
    protected OAuth2Authentication getOAuth2Authentication(ClientDetails client, TokenRequest tokenRequest) {
        Map<String, String> parameters = new LinkedHashMap<>(tokenRequest.getRequestParameters());
        final String tfaToken = parameters.get("tfa_token");

        if (tfaToken == null) {
            throw new InvalidRequestException(MISSING_TFA_TOKEN_MSG);
        }

        OAuth2Authentication authentication = loadAuthentication(tfaToken);

        if (!parameters.containsKey("tfa_code")) {
            throw new InvalidRequestException(MISSING_TFA_CODE_MSG);
        }

        String code = parameters.get("tfa_code");
        String secret = userService.loadUserByUsername(authentication.getName()).getSecret();

        if (!tfaService.verifyCode(secret, code)) {
            throw new InvalidGrantException(INVALID_TFA_CODE_MSG);
        }

        return getAuthentication(tokenRequest, authentication);
    }

    private OAuth2Authentication loadAuthentication(String accessTokenValue) {
        OAuth2AccessToken accessToken = this.tokenStore.readAccessToken(accessTokenValue);

        if (accessToken == null) {
            throw new InvalidTokenException(INVALID_ACCESS_TOKEN_MSG);
        } else if (accessToken.isExpired()) {
            this.tokenStore.removeAccessToken(accessToken);
            throw new InvalidTokenException(ACCESS_TOKEN_EXPIRED_MSG);
        }

        OAuth2Authentication result = this.tokenStore.readAuthentication(accessToken);

        if (result == null) {
            throw new InvalidTokenException(INVALID_ACCESS_TOKEN_MSG);
        }

        return result;
    }

    private OAuth2Authentication getAuthentication(TokenRequest tokenRequest, OAuth2Authentication authentication) {
        Authentication user = authenticationManager.authenticate(authentication.getUserAuthentication());
        Object details = authentication.getDetails();

        authentication = new OAuth2Authentication(authentication.getOAuth2Request(), user);
        authentication.setDetails(details);
        String clientId = authentication.getOAuth2Request().getClientId();

        if (clientId == null || !clientId.equals(tokenRequest.getClientId())) {
            throw new InvalidGrantException(MISSING_CLIENT_MSG);
        }

        if (this.clientDetailsService != null) {
            try {
                this.clientDetailsService.loadClientByClientId(clientId);
            } catch (ClientRegistrationException e) {
                throw new InvalidTokenException(INVALID_CLIENT_MSG + clientId, e);
            }
        }

        return authentication;
    }

}