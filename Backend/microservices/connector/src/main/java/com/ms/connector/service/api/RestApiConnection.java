package com.ms.connector.service.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ms.connector.model.SearchQuery;
import com.ms.connector.service.client.HttpClient;
import com.ms.connector.service.client.RestClient;
import lombok.SneakyThrows;
import org.jsoup.Connection;

public class RestApiConnection extends HttpApiConnection {

    private final ObjectMapper mapper = new ObjectMapper();
    private final RestClient client;

    public RestApiConnection(Connection.Method method, String path) {
        super(method, path);
        this.client = new RestClient(path);
    }

    @Override
    protected HttpClient getClient() {
        return client;
    }

    @SneakyThrows
    @Override
    protected String prepareRequestBody(SearchQuery query) {
        return mapper.writeValueAsString(query);
    }
}
