package com.ms.connector.service.api.converter;

import com.customstarter.model.request.SearchRequestPayload;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ms.connector.dto.response.ApiResponse;
import lombok.SneakyThrows;

public class JsonBodyConverter<Type extends ApiResponse> implements BodyConverter {

    // TODO: probably need to configure objectMapper bean and autowire it in here
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @SneakyThrows
    @Override
    public String payloadToBody(SearchRequestPayload searchRequestPayload) {
        return objectMapper.writeValueAsString(searchRequestPayload);
    }

    @SneakyThrows
    @Override
    public Type responseBodyToObject(String response) {
        return objectMapper.readValue(response, new TypeReference<>() {});
    }
}
