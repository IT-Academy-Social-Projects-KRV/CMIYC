package com.ms.search.connectInterface;

import com.ms.search.model.SearchQuery;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Map;

@FeignClient(name="connector")
public interface ConnectorConnect {

    @PostMapping("/searcher")
    Map<String, Object> searcher(@RequestHeader(value = "Authorization", required = true) String authorizationHeader, @RequestBody SearchQuery searchQuery);
}
