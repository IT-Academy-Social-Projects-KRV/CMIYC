package com.ms.search.connectInterface;

import com.ms.search.config.CaffeineConfig;
import com.ms.search.model.SearchRequest;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Map;

@FeignClient(name = "connector", url = "${routes.connector}")
@CacheConfig(cacheNames = CaffeineConfig.CACHE_SEARCH)
public interface ConnectorConnect {

    @PostMapping("/searcher")
    @Cacheable
    Map<String, Object> searcher(
            @RequestHeader(value = "Authorization") String authorizationHeader, @RequestBody SearchRequest searchRequest
    );

}
