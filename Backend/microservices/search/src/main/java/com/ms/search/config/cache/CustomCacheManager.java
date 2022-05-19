package com.ms.search.config.cache;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.caffeine.CaffeineCacheManager;

import java.util.*;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

@AllArgsConstructor
public class CustomCacheManager extends CaffeineCacheManager {

    private final ObjectMapper mapper;

    public List<SearchRequestWrapper> getRequestHistoryByUser(String token, String cacheName) {
        CaffeineCache caffeineCache = (CaffeineCache) this.getCache(cacheName);
        com.github.benmanes.caffeine.cache.Cache<Object, Object> nativeCache =
                Objects.requireNonNull(caffeineCache).getNativeCache();

        ConcurrentMap<Object, Object> cacheMap = nativeCache.asMap();
        List<SearchRequestWrapper> result = new ArrayList<>();
        String email = getEmailFromToken(token);
        for (Object o : cacheMap.keySet()) {
            if (o instanceof TokenCacheKey) {
                TokenCacheKey key = (TokenCacheKey) o;
                if (key.getEmail() == null) {
                    key.setEmail(getEmailFromToken(key.getToken()));
                }
                if (key.getEmail().equals(email)) {
                    result.add(key.getTimeSearchRequest());
                }
            }
        }
        return result.stream().sorted(Comparator.comparing(SearchRequestWrapper::getDateTime).reversed()).collect(Collectors.toList());
    }

    @SneakyThrows
    public String getEmailFromToken(String token) {
        String[] split = token.split("\\.");
        String payLoad = new String(Base64.getUrlDecoder().decode(split[1]));
        UserLoginWrapper userLoginWrapper = mapper.readValue(payLoad, UserLoginWrapper.class);
        return userLoginWrapper.getUserName();
    }

}
