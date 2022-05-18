package com.ms.connector.service.api;

import com.customstarter.model.request.SearchRequestPayload;
import com.ms.connector.dto.response.WebsocketApiResponse;
import com.ms.connector.service.api.converter.JsonBodyConverter;
import com.ms.connector.service.client.WebsocketClient;

public class WebsocketApiConnection implements ApiConnection {

    private static final JsonBodyConverter<WebsocketApiResponse> converter = new JsonBodyConverter<>();

    private final WebsocketClient client;

    public WebsocketApiConnection(String path, int timeout) {
        this.client = new WebsocketClient(path, timeout);
    }

    @Override
    public ApiConnection.Type getType() {
        return ApiConnection.Type.WEBSOCKET;
    }

    @Override
    public WebsocketApiResponse getData(SearchRequestPayload payload) {
        String body = converter.payloadToBody(payload);
        String response = client.sendAndReceive(body);

        return converter.responseBodyToObject(response);
    }
}