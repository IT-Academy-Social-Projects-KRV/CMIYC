package com.ms.search.service;

import com.customstarter.model.request.SearchRequest;
import com.customstarter.model.response.SearchResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Map;

@FeignClient(name = "connector", url = "${routes.connector}")
public interface ConnectorConnect {

    @PostMapping("/searcher")
    SearchResponse searcher(
            @RequestHeader(value = "Authorization") String authorizationHeader, @RequestBody SearchRequest searchRequest
    );

}
