package com.ms.connector.service.api;

import com.ms.connector.dto.SearchQuery;

public interface ApiConnection {

    String getName();

    ApiConnectionType getType();

    Object getData(SearchQuery query) throws Exception;

}
