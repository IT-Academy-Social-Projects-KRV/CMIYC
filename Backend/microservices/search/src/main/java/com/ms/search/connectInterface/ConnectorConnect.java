package com.ms.search.connectInterface;

import com.customstarter.model.request.SearchRequest;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Map;

@FeignClient(name = "connector", url = "${routes.connector}")
public interface ConnectorConnect {

    @PostMapping("/searcher")
    Map<String, Object> searcher(
            @RequestHeader(value = "Authorization") String authorizationHeader, @RequestBody SearchRequest searchRequest
    );

}
