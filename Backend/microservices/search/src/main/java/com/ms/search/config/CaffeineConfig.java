package com.ms.search.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@EnableCaching
public class CaffeineConfig {

    public final static String CACHE_SCHEMAS = "schemas";
    public final static String CACHE_SEARCH = "search";

    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager();
        caffeineCacheManager.setCacheNames(List.of(CACHE_SCHEMAS, CACHE_SEARCH));
        caffeineCacheManager.setAllowNullValues(false);

        return caffeineCacheManager;
    }
}
