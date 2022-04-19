package com.ms.connector.service.api;

import com.ms.connector.model.SearchQuery;

public abstract class ApiConnection {

    protected final String path;

    public abstract Object getData(SearchQuery query) throws Exception;

    public ApiConnection(String path) {
        this.path = path;
    }
}
