package com.ms.authority.config;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.ClientSettings;
import org.springframework.security.oauth2.server.authorization.config.ProviderSettings;
import org.springframework.security.web.SecurityFilterChain;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;

@Configuration(proxyBeanMethods = false)
public class AuthorizationServerConfig {

    @Autowired
    private PasswordEncoder passwordEncoder;

    /** Applies default oauth2 authorization security. Registers some useful beans for oauth2 auth server
     * @see OAuth2AuthorizationServerConfiguration#applyDefaultSecurity(HttpSecurity)
     */
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain authServerSecurityFilterChain(HttpSecurity http) throws Exception {
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);
        return http.formLogin(Customizer.withDefaults()).build();
    }

    /**
     * Bean for storing list of clients. We will always have one (or two?) hardcoded client so In-Memory implementation should be good enough.
     *
     * @see RegisteredClientRepository <= Repository interface
     * @see InMemoryRegisteredClientRepository <= In-Memory implementation
     * @see JdbcOAuth2AuthorizationConsentService <= Database implementation
     *
     * @see RegisteredClient <= Registered oauth2 client
     * */
    @Bean
    public RegisteredClientRepository registeredClientRepository() {
        // We're creating one simple client with clientId=client-ui and secret=secret
        RegisteredClient registeredClient = RegisteredClient
                .withId(UUID.randomUUID().toString())                           // Random string because we will never need id of client
                .clientId("client-ui")                                          // Public id of client
                .clientSecret(passwordEncoder.encode("secret"))      // TODO: change client secret to some secure string
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC) // TODO: learn about client authentication methods
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)          // TODO: learn about authorization grant types
                .authorizationGrantType(AuthorizationGrantType.PASSWORD)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .redirectUri("http://127.0.0.1:8080/login/oauth2/code/client-ui-oidc")
                .redirectUri("http://127.0.0.1:8080/authorized")                // Url to which user will be redirected after successful login. Must be specified in client settings
                .scope(OidcScopes.OPENID)                                       // Can be used for direct login from client on auth server. Useful for our admin ui functionality
                .scope("user")                                                  // We are probably need only one scope - user
                .clientSettings(ClientSettings.builder().requireAuthorizationConsent(false).build())     // TODO: learn about requireProofKey. Probably can be used to implement 2FAuthorization
                .build();

        // Return in-memory implementation of repository
        return new InMemoryRegisteredClientRepository(registeredClient);
    }

    /**
     * Authorization server provider settings
     * Issuer url must be specified in resource server config
     * We can also change some important endpoints like authorization endpoint, token endpoint, etc..
     *
     * @see ProviderSettings
     */
    @Bean
    public ProviderSettings providerSettings() {
        // Return default provider settings, change only issuer url. Issuer url is basically url of this application
        // TODO: probably need to load this url from config file instead of hardcoding it
        return ProviderSettings
                .builder()
                .issuer("http://localhost:8090")
                .build();
    }

    // ===>>> https://youtu.be/MOCeQYbQPPU?t=2109
    // "This is the standard, you don't have to change anything. Okay?"
    @Bean
    public JWKSource<SecurityContext> jwkSource() {
        RSAKey rsaKey = generateRsa();
        JWKSet jwkSet = new JWKSet(rsaKey);
        return (jwkSelector, securityContext) -> jwkSelector.select(jwkSet);
    }

    private static RSAKey generateRsa() {
        KeyPair keyPair = generateRsaKey();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        return new RSAKey.Builder(publicKey)
                .privateKey(privateKey)
                .keyID(UUID.randomUUID().toString())
                .build();
    }

    private static KeyPair generateRsaKey() {
        KeyPair keyPair;
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            keyPair = keyPairGenerator.generateKeyPair();
        } catch (Exception ex) {
            throw new IllegalStateException(ex);
        }
        return keyPair;
    }
}
