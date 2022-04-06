package com.ms.authority.config;

import com.ms.authority.utils.FrontendData;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.KeyUse;
import com.nimbusds.jose.jwk.RSAKey;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.*;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import java.security.KeyPair;
import java.security.interfaces.RSAPublicKey;
import java.util.*;
import java.util.stream.Collectors;

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

    @Override
    public void configure(final AuthorizationServerSecurityConfigurer oauthServer) {
        oauthServer
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()");
    }

    @Override
    public void configure(final ClientDetailsServiceConfigurer clients) throws Exception {
        clients
                .inMemory()
                    .withClient("client-ui")
                    .secret(passwordEncoder.encode("secret"))
                    .authorizedGrantTypes("password")
                    .scopes("user", "admin")                // We will load scopes manually, so we don't need this field
                    .accessTokenValiditySeconds(3600 * 2)   // 2 hours
                    .refreshTokenValiditySeconds(0);        // ?????
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
        tokenEnhancerChain
                .setTokenEnhancers(Arrays.asList(customTokenEnhancer(), accessTokenConverter()));

        endpoints
                .tokenStore(tokenStore())
                .tokenEnhancer(tokenEnhancerChain)
                .authenticationManager(authentication -> authenticationProvider.authenticate(authentication));
    }

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }

    // Об'єкт що "прикрашає" наш JWT токен - додає додаткові дані перед відправкою клієнту
    @Bean
    public TokenEnhancer customTokenEnhancer() {
        return (accessToken, authentication) -> {
            // data це json об'єкт що повернеться на фронт
            Map<String, Object> data = new HashMap<>();

            FrontendData frontendData = (FrontendData) authentication.getUserAuthentication().getDetails();
            // Додаємо в data email та повне ім'я юзера. На етапі аутентифікації ми поклали цей об'єкт сюди в класі AuthenticationProviderImpl
            frontendData.loadToMap(data);

            // Збираємо список ролей користувача в один рядок через кому
            String scopes = authentication
                    .getAuthorities()
                    .stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.joining(", "));

            // Кладемо ролі юзера на заміну тим що були вказані при реєстрації клієнта в конфігу
            data.put("scope", scopes);        // Поле scope кладеться спрінгом

            // Якщо є більше однієї scope, їх треба обов'язково покласти в поле scopes, інакше resource server не зрозуміє :(
            if(authentication.getAuthorities().size() > 1)
                data.put("scopes", scopes);

            ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(data);
            return accessToken;
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

}
