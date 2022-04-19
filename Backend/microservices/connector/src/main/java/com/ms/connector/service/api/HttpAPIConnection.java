package com.ms.connector.service.api;

import org.jsoup.Connection;

public abstract class HttpAPIConnection extends APIConnection {

    protected final Connection.Method method;

    public HttpAPIConnection(Connection.Method method, String path) {
        super(path);
        this.method = method;
    }
}
