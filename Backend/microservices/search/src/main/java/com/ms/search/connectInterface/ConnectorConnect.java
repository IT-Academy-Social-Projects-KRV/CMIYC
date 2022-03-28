package com.ms.search.connectInterface;

import com.ms.search.model.SearchQuery;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(name="connector")
public interface ConnectorConnect {
    @PostMapping("/api/searcher/")
    public Map<String, Map<String, String>> searcher(@RequestBody SearchQuery searchQuery);

    @GetMapping("/api/searcher/")
    public Map<String, Map<String, String>> testQuery();
}
