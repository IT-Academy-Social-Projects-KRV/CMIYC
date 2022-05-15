package com.ms.data.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class JWTSecurityConfigData extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests(authz -> authz
                        .antMatchers(HttpMethod.GET, "/schemas/selected")
                            .hasAnyAuthority("SCOPE_user", "SCOPE_admin_schema")
                        .anyRequest().hasAuthority("SCOPE_admin_schema"))
                .oauth2ResourceServer(oauth2 -> oauth2.jwt());
    }
}
