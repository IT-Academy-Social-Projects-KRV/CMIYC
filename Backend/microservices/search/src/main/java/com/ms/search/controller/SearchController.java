package com.ms.search.controller;

import com.customstarter.model.response.SearchResponse;
import com.customstarter.model.schema.Schema;
import com.customstarter.utils.ApiError;
import com.customstarter.model.request.SearchRequest;
import com.ms.search.service.CacheService;
import com.ms.search.service.ConnectorConnect;
import com.ms.search.service.DataConnect;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
public class SearchController {

    private final DataConnect dataConnect;
    private final ConnectorConnect connectorConnect;
    private final CacheService cacheService;



    @SneakyThrows
    @GetMapping
    @RequestMapping("/schema")
    public ResponseEntity<Schema> getSchema(@RequestHeader(value = "Authorization") String authorizationHeader) {
        return new ResponseEntity<>(
                dataConnect.getCurrentSchemaForm(authorizationHeader),
                HttpStatus.OK
        );
    }

    @PostMapping
    @RequestMapping("/search")
    public ResponseEntity<?> search(
            @RequestHeader(value = "Authorization") String authorizationHeader,
            @RequestBody SearchRequest searchRequest,
            JwtAuthenticationToken jwtAuthenticationToken

    ) {
        System.out.println(searchRequest.toString());

        try {
            SearchResponse searchResponse = connectorConnect.searcher(authorizationHeader, searchRequest);
            cacheService.save(jwtAuthenticationToken, searchResponse);
            return new ResponseEntity<>(searchResponse, HttpStatus.OK);
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseEntity.internalServerError().body(new ApiError(exception.getMessage()));
        }
    }

    @GetMapping("/search/history")
    public List<SearchResponse> getCacheByUser(JwtAuthenticationToken jwtAuthenticationToken){
        return cacheService.findAll(jwtAuthenticationToken);
    }

}
