package com.ms.search.connectInterface;

import com.ms.search.config.CaffeineConfig;
import com.ms.search.model.XmlObject;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import javax.xml.bind.JAXBException;

import java.util.List;

@FeignClient(name = "data", url = "${ROUTES_MICROSERVICES_DATA}")
@CacheConfig(cacheNames = CaffeineConfig.CACHE_SCHEMAS)
public interface DataConnect {

    @GetMapping("/schemas")
    List<XmlObject> xmlSchema(@RequestHeader(value = "Authorization") String authorizationHeader) throws JAXBException;

}
