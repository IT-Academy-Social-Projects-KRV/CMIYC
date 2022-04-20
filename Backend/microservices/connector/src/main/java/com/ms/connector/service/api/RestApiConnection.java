package com.ms.connector.service.api;

import com.ms.connector.service.api.converter.BodyConverter;
import com.ms.connector.service.api.converter.JsonBodyConverter;
import com.ms.connector.service.client.RestClient;
import org.jsoup.Connection;

public class RestApiConnection extends HttpApiConnection {

    private static final BodyConverter converter = new JsonBodyConverter();

    public RestApiConnection(String name, String path, Connection.Method method) {
        super(method, name, new RestClient(path), converter);
    }

    @Override
    public ApiConnection.Type getType() {
        return ApiConnection.Type.REST;
    }
}
