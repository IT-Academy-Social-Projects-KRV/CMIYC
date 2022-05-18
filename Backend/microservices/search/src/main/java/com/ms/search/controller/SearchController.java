package com.ms.search.controller;

import com.customstarter.model.schema.Schema;
import com.customstarter.utils.ApiError;
import com.ms.search.connectInterface.ConnectorConnect;
import com.ms.search.connectInterface.DataConnect;
import com.customstarter.model.request.SearchRequest;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SearchController {

    private final DataConnect dataConnect;
    private final ConnectorConnect connectorConnect;

    public SearchController(DataConnect dataConnect, ConnectorConnect connectorConnect) {
        this.dataConnect = dataConnect;
        this.connectorConnect = connectorConnect;
    }

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
