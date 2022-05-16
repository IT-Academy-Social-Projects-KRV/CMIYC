package com.ms.search.controller;

import com.ms.search.config.CaffeineConfig;
import com.ms.search.connectInterface.ConnectorConnect;
import com.ms.search.connectInterface.DataConnect;
import com.ms.search.model.SearchQuery;
import lombok.AllArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.Set;
import java.util.function.ToDoubleBiFunction;

@RestController
@RequestMapping("/search")
@AllArgsConstructor
public class SearchController {

    private final DataConnect dataConnect; //todo change connect into dataApiClient
    private final ConnectorConnect connectorConnect; //todo connectorAPI
    private final CacheManager cacheManager;

    //todo controller advice (google it) - add global exception handler

    @GetMapping
    public ResponseEntity<?> getSchemas(@RequestHeader(value = "Authorization") String authorizationHeader) {
        try {
            return new ResponseEntity<> (dataConnect.xmlSchema(authorizationHeader), HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    @RequestMapping("/cached")
    public Object getCache() {
        CaffeineCache caffeineCache = (CaffeineCache) cacheManager.getCache(CaffeineConfig.CACHE_SEARCH);
        com.github.benmanes.caffeine.cache.Cache<Object, Object> nativeCache =
                Objects.requireNonNull(caffeineCache).getNativeCache();
        System.out.println(nativeCache.asMap().keySet());
        return nativeCache.asMap().values();
    }


    @PostMapping
    public ResponseEntity<?> search(@RequestHeader(value = "Authorization") String authorizationHeader, @RequestBody SearchQuery searchQuery) {
        try {
            return new ResponseEntity<>(connectorConnect.searcher(authorizationHeader, searchQuery), HttpStatus.OK);
            //!todo rename searcher --> search
        } catch (Exception exception) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
