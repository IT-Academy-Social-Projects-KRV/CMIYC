package com.ms.connector.service.api;

import com.customstarter.model.request.SearchRequestPayload;
import com.customstarter.model.response.SearchResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ms.connector.dto.response.RestApiResponse;
import com.ms.connector.service.client.RestClient;
import lombok.SneakyThrows;
import org.jsoup.Connection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RestApiConnection implements ApiConnection {

    private final ObjectMapper objectMapper;

    private final String name;
    private final Connection.Method method;
    private final RestClient client;

    public RestApiConnection(
            @Autowired ObjectMapper objectMapper,
            @Value("${api.rest.name}") String name,
            @Value("${api.rest.path}") String path,
            @Value("${api.rest.method}") String method
    ) {
        this.objectMapper = objectMapper;

        this.name = name;
        this.method = Connection.Method.valueOf(method.toUpperCase());
        this.client = new RestClient(path);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public ApiConnection.Type getType() {
        return ApiConnection.Type.REST;
    }

    @SneakyThrows
    @Override
    public RestApiResponse loadData(SearchRequestPayload payload) {
        String requestBody = objectMapper.writeValueAsString(payload);
        String response = client.request(method, "", requestBody);

        return objectMapper.readValue(response, RestApiResponse.class);
    }

    @Override
    public RestApiResponse loadDataAndSaveToResponse(SearchRequestPayload payload, SearchResponse response) {
        RestApiResponse restApiResponse = loadData(payload);
        response.setApi1Responses(restApiResponse.getData());

        return restApiResponse;
    }
}
