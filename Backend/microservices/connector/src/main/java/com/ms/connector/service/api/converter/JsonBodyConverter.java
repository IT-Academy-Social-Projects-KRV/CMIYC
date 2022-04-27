package com.ms.connector.service.api.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ms.connector.dto.SearchQuery;
import com.ms.connector.dto.SearchResponse;
import lombok.SneakyThrows;

public class JsonBodyConverter implements BodyConverter {

    // TODO: probably need to configure objectMapper bean and autowire it in here
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @SneakyThrows
    @Override
    public String queryToRequestBody(SearchQuery query) {
        return objectMapper.writeValueAsString(query.toMap());
    }

    @SneakyThrows
    @Override
    public SearchResponse responseBodyToObject(String response) {
        return objectMapper.readValue(response, SearchResponse.class);
    }
}
