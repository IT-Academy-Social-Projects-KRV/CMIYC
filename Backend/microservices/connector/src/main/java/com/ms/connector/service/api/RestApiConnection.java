package com.ms.connector.service.api;

import com.ms.connector.service.api.converter.BodyConverter;
import com.ms.connector.service.api.converter.JsonBodyConverter;
import com.ms.connector.service.client.HttpClient;
import com.ms.connector.service.client.RestClient;
import org.jsoup.Connection;

public class RestApiConnection extends HttpApiConnection {

    private static final BodyConverter converter = new JsonBodyConverter();

    private final String name;
    private final RestClient client;

    public RestApiConnection(String name, String path, Connection.Method method) {
        super(method);
        this.name = name;
        this.client = new RestClient(path);
    }

    @Override
    protected HttpClient getClient() {
        return client;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public ApiConnectionType getType() {
        return ApiConnectionType.REST;
    }

    @Override
    protected BodyConverter getBodyConverter() {
        return converter;
    }
}
