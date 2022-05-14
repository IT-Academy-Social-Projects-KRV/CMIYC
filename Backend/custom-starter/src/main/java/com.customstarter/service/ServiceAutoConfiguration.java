package com.customstarter.service;

import com.customstarter.config.CorsFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceAutoConfiguration {

    @Bean
    public CorsFilter corsFilter() {
        return new CorsFilter();
    }
}
