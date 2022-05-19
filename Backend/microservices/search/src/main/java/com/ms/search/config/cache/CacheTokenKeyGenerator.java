package com.ms.search.config.cache;

import com.customstarter.model.request.SearchRequest;
import org.springframework.cache.interceptor.KeyGenerator;

import java.lang.reflect.Method;

public class CacheTokenKeyGenerator implements KeyGenerator {



    @Override
    public Object generate(Object target, Method method, Object... params) {
        String token = (String) params[0];
        if (params.length < 2) {
            return token;
        } else {
            return new TokenCacheKey(token, new SearchRequestWrapper((SearchRequest) params[1]));
        }
    }
}
