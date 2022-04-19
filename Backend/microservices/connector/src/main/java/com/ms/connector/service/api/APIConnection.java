package com.ms.connector.service.api;

import com.ms.connector.model.SearchQuery;
import com.ms.connector.service.client.AbstractClient;

public abstract class APIConnection {

    protected final String path;

    public abstract Object getData(SearchQuery query) throws Exception;

    public APIConnection(String path) {
        this.path = path;
    }
}
