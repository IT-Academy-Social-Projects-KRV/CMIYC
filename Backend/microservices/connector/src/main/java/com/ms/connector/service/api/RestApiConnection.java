package com.ms.connector.service.api;

import com.customstarter.model.request.SearchRequestPayload;
import com.ms.connector.dto.response.RestApiResponse;
import com.ms.connector.service.api.converter.JsonBodyConverter;
import com.ms.connector.service.client.RestClient;
import org.jsoup.Connection;

public class RestApiConnection implements ApiConnection {

    private static final JsonBodyConverter<RestApiResponse> converter = new JsonBodyConverter<>();

    private final Connection.Method method;
    private final RestClient client;

    public RestApiConnection(String path, Connection.Method method) {
        this.method = method;
        this.client = new RestClient(path);
    }

    @Override
    public ApiConnection.Type getType() {
        return ApiConnection.Type.REST;
    }

    @Override
    public RestApiResponse getData(SearchRequestPayload payload) {
        String requestBody = converter.payloadToBody(payload);
        String response = client.request(method, "", requestBody);

        return converter.responseBodyToObject(response);
    }
}
