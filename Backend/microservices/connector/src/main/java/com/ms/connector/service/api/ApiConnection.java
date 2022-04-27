package com.ms.connector.service.api;

import com.ms.connector.dto.SearchQuery;
import com.ms.connector.dto.SearchResponse;

public interface ApiConnection {

    String getName();

    Type getType();

    SearchResponse getData(SearchQuery query) throws Exception;

    enum Type {

        REST, SOAP, WEBSOCKET
    }
}
