package com.ms.search.config.cache;

import com.customstarter.model.request.SearchRequest;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class SearchRequestWrapper {
    private final LocalDateTime dateTime = LocalDateTime.now();
    private final SearchRequest searchRequest;

    public SearchRequestWrapper(SearchRequest searchRequest) {
        this.searchRequest = searchRequest;
    }
}
