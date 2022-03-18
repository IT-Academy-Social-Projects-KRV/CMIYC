package com.ms.authority.config;

import com.ms.authority.service.AuthenticationProviderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

// Web security config
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private AuthenticationProviderImpl authenticationProvider;

    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                // Any request from user must be authenticated
                .authorizeRequests(authorizeRequests -> authorizeRequests.anyRequest().authenticated())
                // Default login form, we can change it later
                .formLogin(Customizer.withDefaults());

        return http.build();
    }

    /**
     * We telling spring security to use out custom authenticationProvider
     *
     * @see AuthenticationProviderImpl
     * @see AuthenticationProvider
     */
    @Autowired
    public void bindAuthenticationProvider(AuthenticationManagerBuilder builder) {
        builder.authenticationProvider(authenticationProvider);
    }
}
