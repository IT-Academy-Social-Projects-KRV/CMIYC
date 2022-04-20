package com.ms.connector.service.api;

import com.ms.connector.dto.SearchQuery;

public interface ApiConnection {

    String getName();

    Type getType();

    Object getData(SearchQuery query) throws Exception;

    enum Type {

        REST, SOAP, WEBSOCKET
    }
}
