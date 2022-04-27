package com.ms.search.controller;

import com.ms.search.connectInterface.ConnectorConnect;
import com.ms.search.connectInterface.DataConnect;
import com.ms.search.model.SearchQuery;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/search")
public class SearchController {

    private final DataConnect dataConnect;
    private final ConnectorConnect connectorConnect;

    public SearchController(DataConnect dataConnect, ConnectorConnect connectorConnect) {
        this.dataConnect = dataConnect;
        this.connectorConnect = connectorConnect;
    }

    @GetMapping
    public ResponseEntity<?> getSchemas(@RequestHeader(value = "Authorization") String authorizationHeader) {
        try {
            return new ResponseEntity<> (dataConnect.xmlSchema(authorizationHeader), HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<?> search(@RequestHeader(value = "Authorization") String authorizationHeader, @RequestBody SearchQuery searchQuery) {
        try {
            return new ResponseEntity<>(connectorConnect.searcher(authorizationHeader, searchQuery), HttpStatus.OK);
        } catch (Exception exception) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
