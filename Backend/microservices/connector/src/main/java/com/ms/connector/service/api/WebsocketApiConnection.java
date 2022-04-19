package com.ms.connector.service.api;

import com.ms.connector.dto.SearchQuery;
import com.ms.connector.service.api.converter.BodyConverter;
import com.ms.connector.service.api.converter.JsonBodyConverter;
import com.ms.connector.service.client.WebsocketClient;

public class WebsocketApiConnection implements ApiConnection {

    private static final BodyConverter converter = new JsonBodyConverter();

    private final String name;
    private final WebsocketClient client;

    public WebsocketApiConnection(String name, String path) {
        this(name, path, 5 * 1000);
    }

    public WebsocketApiConnection(String name, String path, int timeout) {
        this.name = name;
        this.client = new WebsocketClient(path, timeout);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public ApiConnectionType getType() {
        return ApiConnectionType.WEBSOCKET;
    }

    @Override
    public Object getData(SearchQuery query) {
        String body = converter.queryToRequestBody(query);
        String response = client.sendAndReceive(body);

        return converter.responseBodyToObject(response);
    }
}