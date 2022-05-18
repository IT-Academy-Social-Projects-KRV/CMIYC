package com.ms.search.cache;

import org.springframework.cache.interceptor.KeyGenerator;

import java.lang.reflect.Method;

public class CacheTokenKeyGenerator implements KeyGenerator {
    @Override
    public Object generate(Object target, Method method, Object... params) {
        String token = (String) params[0];
        return new TokenCacheKey(token, params);
    }
}
