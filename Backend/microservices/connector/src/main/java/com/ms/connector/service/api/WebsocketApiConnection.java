package com.ms.connector.service.api;

import com.customstarter.model.request.SearchRequestPayload;
import com.customstarter.model.response.SearchResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ms.connector.dto.response.WebsocketApiResponse;
import com.ms.connector.service.client.WebsocketClient;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class WebsocketApiConnection implements ApiConnection {

    private final ObjectMapper objectMapper;

    private final String name;
    private final WebsocketClient client;

    public WebsocketApiConnection(
            @Autowired ObjectMapper objectMapper,
            @Value("${api.websocket.name}") String name,
            @Value("${api.websocket.path}") String path,
            @Value("${api.websocket.timeout}") int timeout
    ) {
        this.objectMapper = objectMapper;

        this.name = name;
        this.client = new WebsocketClient(path, timeout);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public ApiConnection.Type getType() {
        return ApiConnection.Type.WEBSOCKET;
    }

    @SneakyThrows
    @Override
    public WebsocketApiResponse loadData(SearchRequestPayload payload) {
        String body = objectMapper.writeValueAsString(payload);
        String response = client.sendAndReceive(body);

        return objectMapper.readValue(response, WebsocketApiResponse.class);
    }

    @Override
    public WebsocketApiResponse loadDataAndSaveToResponse(SearchRequestPayload payload, SearchResponse response) {
        WebsocketApiResponse websocketApiResponse = loadData(payload);
        response.setApi3Responses(websocketApiResponse.getData());

        return websocketApiResponse;
    }
}