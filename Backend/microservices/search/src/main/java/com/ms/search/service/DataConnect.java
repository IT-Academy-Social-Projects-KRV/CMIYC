package com.ms.search.service;

import com.customstarter.model.schema.Schema;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import javax.xml.bind.JAXBException;

@FeignClient(name = "data", url = "${routes.data}")
public interface DataConnect {

    @GetMapping("/schemas/selected")
    Schema getCurrentSchemaForm(@RequestHeader(value = "Authorization") String authorizationHeader) throws JAXBException;

}
