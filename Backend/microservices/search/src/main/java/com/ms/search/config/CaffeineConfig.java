package com.ms.search.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ms.search.cache.CacheTokenKeyGenerator;
import com.ms.search.cache.CustomCacheManager;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@EnableCaching
public class CaffeineConfig implements CachingConfigurer {

    public final static String CACHE_SCHEMAS = "schemas";
    public final static String CACHE_SEARCH = "search";

    @Bean
    public CustomCacheManager cacheManager(ObjectMapper mapper) {
        CustomCacheManager customCacheManager = new CustomCacheManager(mapper);
        customCacheManager.setCacheNames(List.of(CACHE_SCHEMAS, CACHE_SEARCH));
        customCacheManager.setAllowNullValues(false);
        return customCacheManager;
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return new CacheTokenKeyGenerator();
    }
}
