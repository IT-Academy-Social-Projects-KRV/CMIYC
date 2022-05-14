package com.ms.search.connectInterface;

import com.ms.search.config.CaffeineConfig;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import javax.xml.bind.JAXBException;

@FeignClient(name = "data", url = "${routes.data}")
@CacheConfig(cacheNames = CaffeineConfig.CACHE_SCHEMAS)
public interface DataConnect {

    @GetMapping("/schemas/selected")
    @Cacheable
    Object getCurrentSchema(@RequestHeader(value = "Authorization") String authorizationHeader) throws JAXBException;

}
