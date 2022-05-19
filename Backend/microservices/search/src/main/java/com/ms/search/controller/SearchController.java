package com.ms.search.controller;

import com.ms.search.config.CaffeineConfig;
import com.ms.search.config.cache.CustomCacheManager;
import com.customstarter.model.schema.Schema;
import com.customstarter.utils.ApiError;
import com.ms.search.config.cache.SearchRequestWrapper;
import com.ms.search.connectInterface.ConnectorConnect;
import com.ms.search.connectInterface.DataConnect;
import com.customstarter.model.request.SearchRequest;
import lombok.SneakyThrows;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class SearchController {

    private final DataConnect dataConnect; //todo change connect into dataApiClient
    private final ConnectorConnect connectorConnect; //todo connectorAPI
    private final CustomCacheManager cacheManager;

    //todo controller advice (google it) - add global exception handler

    @SneakyThrows
    @GetMapping
    @RequestMapping("/schema")
    public ResponseEntity<Schema> getSchema(@RequestHeader(value = "Authorization") String authorizationHeader) {
        return new ResponseEntity<>(
                dataConnect.getCurrentSchemaForm(authorizationHeader),
                HttpStatus.OK
        );
    }

    @GetMapping
    @RequestMapping("/search/history")
    public List<SearchRequestWrapper> getCache(@RequestHeader(value = "Authorization") String authorizationHeader) {
        return cacheManager.getRequestHistoryByUser(authorizationHeader, CaffeineConfig.CACHE_SEARCH);
    }


    @PostMapping
    @RequestMapping("/search")
    public ResponseEntity<?> search(
            @RequestHeader(value = "Authorization") String authorizationHeader,
            @RequestBody SearchRequest searchRequest
    ) {
        System.out.println(searchRequest.toString());

        try {
            return new ResponseEntity<>(connectorConnect.searcher(authorizationHeader, searchRequest), HttpStatus.OK);
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseEntity.internalServerError().body(new ApiError(exception.getMessage()));
        }
    }
}
