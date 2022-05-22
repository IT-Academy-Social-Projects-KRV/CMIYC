package com.ms.connector.service.api;

import com.customstarter.model.request.SearchRequestPayload;
import com.customstarter.model.response.SearchResponse;
import com.ms.connector.dto.response.ApiResponse;

public interface ApiConnection {

    String getName();

    Type getType();

    ApiResponse loadData(SearchRequestPayload payload) throws Exception;

    ApiResponse loadDataAndSaveToResponse(SearchRequestPayload payload, SearchResponse response);

    enum Type {
        REST, SOAP, WEBSOCKET
    }
}
