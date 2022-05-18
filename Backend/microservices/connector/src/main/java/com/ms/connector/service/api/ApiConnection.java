package com.ms.connector.service.api;

import com.customstarter.model.request.SearchRequestPayload;
import com.ms.connector.dto.response.ApiResponse;

public interface ApiConnection {

    Type getType();

    ApiResponse getData(SearchRequestPayload payload) throws Exception;

    enum Type {
        REST, SOAP, WEBSOCKET
    }
}
