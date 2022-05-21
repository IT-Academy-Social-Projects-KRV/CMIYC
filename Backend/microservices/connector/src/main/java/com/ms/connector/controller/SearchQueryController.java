package com.ms.connector.controller;

import com.customstarter.model.request.SearchRequest;
import com.customstarter.model.response.SearchResponse;
import com.ms.connector.service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/searcher")
public class SearchQueryController {

    @Autowired
    private ApiService apiService;

    @PostMapping
    public SearchResponse searcher(@RequestBody SearchRequest searchRequest) {
        return apiService.handleSearchRequest(searchRequest);
    }
}
