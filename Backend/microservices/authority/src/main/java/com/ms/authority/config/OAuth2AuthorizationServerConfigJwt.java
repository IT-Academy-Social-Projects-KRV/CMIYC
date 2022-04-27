package com.ms.authority.config;

import com.ms.authority.config.granters.PasswordTokenGranter;
import com.ms.authority.config.granters.TfaTokenGranter;
import com.ms.authority.dto.FrontendData;
import com.ms.authority.service.TfaService;
import com.ms.authority.service.UserService;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.KeyUse;
import com.nimbusds.jose.jwk.RSAKey;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.CompositeTokenGranter;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import java.security.KeyPair;
import java.security.interfaces.RSAPublicKey;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.ms.authority.utils.Authorities.PASSWORD_GRANT_TYPE;
import static com.ms.authority.utils.Authorities.TFA_GRANT_TYPE;

@Configuration
@EnableAuthorizationServer
@AllArgsConstructor
public class OAuth2AuthorizationServerConfigJwt extends AuthorizationServerConfigurerAdapter {

    private static final String KEY_STORE_FILE = "jwt.jks";
    private static final String KEY_ALIAS = "cmiyc-authority-jwt";
    private static final String KEY_STORE_PASSWORD = "*XNU@K(@KxAO@)";
    private static final String JWK_KID = "key-id";

    private final AuthenticationProvider authenticationProvider;
    private final BCryptPasswordEncoder passwordEncoder;
    private final TfaService tfaService;
    private final UserService userService;

    @Override
    public void configure(final AuthorizationServerSecurityConfigurer oauthServer) {
        oauthServer.tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()");
    }

    @Override
    public void configure(final ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("client-ui")
                .secret(passwordEncoder.encode("secret"))
                .authorizedGrantTypes(PASSWORD_GRANT_TYPE, TFA_GRANT_TYPE)
                .scopes("user", "admin_user", "admin_schema", "pre_auth")
                .accessTokenValiditySeconds(3600)
                .refreshTokenValiditySeconds(0);
    }

    @Bean
    @Primary
    public DefaultTokenServices tokenServices() {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore());
        defaultTokenServices.setSupportRefreshToken(false);

        return defaultTokenServices;
    }

    @Override
    public void configure(final AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(customTokenEnhancer(), accessTokenConverter()));

        endpoints.tokenStore(tokenStore())
                .tokenEnhancer(tokenEnhancerChain)
                .tokenGranter(tokenGranter(endpoints));
    }

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }

    @Bean
    public TokenEnhancer customTokenEnhancer() {
        return (accessToken, authentication) -> {
            Map<String, Object> data = new HashMap<>();

            if (authentication.getUserAuthentication().getDetails() != null) {
                FrontendData frontendData = (FrontendData) authentication.getUserAuthentication().getDetails();
                frontendData.loadToMap(data);
            }

            Set<String> scopes = authentication.getAuthorities()
                    .stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toSet());

            DefaultOAuth2AccessToken defaultOAuth2AccessToken = (DefaultOAuth2AccessToken) accessToken;
            defaultOAuth2AccessToken.setAdditionalInformation(data);
            defaultOAuth2AccessToken.setScope(scopes);

            return defaultOAuth2AccessToken;
        };
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        Map<String, String> customHeaders = Collections.singletonMap("kid", JWK_KID);
        JwtAccessTokenConverter converter = new JwtCustomHeadersAccessTokenConverter(customHeaders, keyPair());

        DefaultAccessTokenConverter defaultAccessTokenConverter = new DefaultAccessTokenConverter() {
            @Override
            public OAuth2Authentication extractAuthentication(Map<String, ?> claims) {

                OAuth2Authentication authentication = super.extractAuthentication(claims);
                authentication.setDetails(claims);
                return authentication;
            }
        };

        converter.setAccessTokenConverter(defaultAccessTokenConverter);

        return converter;
    }

    @Bean
    public KeyPair keyPair() {
        ClassPathResource ksFile = new ClassPathResource(KEY_STORE_FILE);
        KeyStoreKeyFactory ksFactory = new KeyStoreKeyFactory(ksFile, KEY_STORE_PASSWORD.toCharArray());

        return ksFactory.getKeyPair(KEY_ALIAS);
    }

    @Bean
    public JWKSet jwkSet() {
        RSAKey.Builder builder = new RSAKey
                .Builder((RSAPublicKey) keyPair().getPublic())
                .keyUse(KeyUse.SIGNATURE)
                .algorithm(JWSAlgorithm.RS256)
                .keyID(JWK_KID);

        return new JWKSet(builder.build());
    }

    private TokenGranter tokenGranter(final AuthorizationServerEndpointsConfigurer endpoints) {
        List<TokenGranter> granters = new ArrayList<>();
        granters.add(new PasswordTokenGranter(endpoints, authenticationProvider::authenticate));
        granters.add(new TfaTokenGranter(endpoints, authenticationProvider::authenticate, tfaService, userService));

        return new CompositeTokenGranter(granters);
    }

}
