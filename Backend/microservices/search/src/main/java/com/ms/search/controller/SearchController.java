package com.ms.search.controller;

import com.ms.search.connectInterface.ConnectorConnect;
import com.ms.search.connectInterface.DataConnect;
import com.ms.search.model.SearchQuery;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/search")
public class SearchController {

    private final DataConnect dataConnect;
    private final ConnectorConnect connectorConnect;

    public SearchController(DataConnect dataConnect, ConnectorConnect connectorConnect) {
        this.dataConnect = dataConnect;
        this.connectorConnect = connectorConnect;
    }

    @GetMapping
    public ResponseEntity<?> getSchemas() {
        try {
            return new ResponseEntity<> (dataConnect.welcomeFromData(), HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<?> search(@RequestBody SearchQuery searchQuery) {
        try {
            return new ResponseEntity<>(connectorConnect.testQuery(), HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
