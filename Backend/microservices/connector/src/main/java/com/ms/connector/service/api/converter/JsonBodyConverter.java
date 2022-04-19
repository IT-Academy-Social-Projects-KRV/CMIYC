package com.ms.connector.service.api.converter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ms.connector.dto.SearchQuery;
import lombok.SneakyThrows;

import java.util.HashMap;

public class JsonBodyConverter implements BodyConverter {

    // TODO: probably need to configure objectMapper bean and autowire it in here
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final TypeReference<HashMap<String,Object>> typeRef = new TypeReference<>() {};

    @SneakyThrows
    @Override
    public String queryToRequestBody(SearchQuery query) {
        return objectMapper.writeValueAsString(query.toMap());
    }

    @SneakyThrows
    @Override
    public Object responseBodyToObject(String response) {
        return objectMapper.readValue(response, typeRef);
    }
}
