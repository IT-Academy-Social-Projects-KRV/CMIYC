package com.ms.connector.service.api.converter;

import com.customstarter.model.request.SearchRequestPayload;
import com.ms.connector.dto.response.ApiResponse;

public interface BodyConverter {

    String payloadToBody(SearchRequestPayload payload);

    ApiResponse responseBodyToObject(String response);
}
