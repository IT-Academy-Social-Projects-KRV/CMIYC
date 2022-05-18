package com.ms.search.controller;

import com.ms.search.config.CaffeineConfig;
import com.ms.search.cache.CustomCacheManager;
import com.ms.search.connectInterface.ConnectorConnect;
import com.ms.search.connectInterface.DataConnect;
import com.ms.search.model.SearchQuery;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/search")
@AllArgsConstructor
public class SearchController {

    private final DataConnect dataConnect; //todo change connect into dataApiClient
    private final ConnectorConnect connectorConnect; //todo connectorAPI
    private final CustomCacheManager cacheManager;

    //todo controller advice (google it) - add global exception handler

    @GetMapping
    public ResponseEntity<?> getSchemas(@RequestHeader(value = "Authorization") String authorizationHeader) {
        try {
            return new ResponseEntity<>(dataConnect.xmlSchema(authorizationHeader), HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    @RequestMapping("/cached")
    public Object getCache(@RequestHeader(value = "Authorization") String authorizationHeader) {
        return cacheManager.getDataForUser(authorizationHeader, CaffeineConfig.CACHE_SEARCH);
    }


    @PostMapping
    public ResponseEntity<?> search(@RequestHeader(value = "Authorization") String authorizationHeader, @RequestBody SearchQuery searchQuery) {
        try {
            return new ResponseEntity<>(connectorConnect.search(authorizationHeader, searchQuery), HttpStatus.OK);
            //!todo rename searcher --> search
        } catch (Exception exception) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
