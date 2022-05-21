package com.ms.connector.service;

import com.customstarter.model.request.SearchRequest;
import com.customstarter.model.request.SearchRequestPayload;
import com.customstarter.model.response.SearchResponse;
import com.ms.connector.dto.response.ApiResponse;
import com.ms.connector.exception.ApiErrorException;
import com.ms.connector.service.api.RestApiConnection;
import com.ms.connector.service.api.SoapApiConnection;
import com.ms.connector.service.api.WebsocketApiConnection;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ApiService {

    private final RestApiConnection restApiConnection;
    private final SoapApiConnection soapApiConnection;
    private final WebsocketApiConnection websocketApiConnection;

    public SearchResponse handleSearchRequest(SearchRequest request) {
        SearchResponse searchResponse = new SearchResponse();
        SearchRequestPayload payload = request.getData();

        for (String apiName : request.getApis()) {
            ApiResponse response = null;

            try {
                if(apiName.equals(restApiConnection.getName())) {
                    response = restApiConnection.loadDataAndSaveToResponse(payload, searchResponse);
                } else if(apiName.equals(soapApiConnection.getName())) {
                    response = soapApiConnection.loadDataAndSaveToResponse(payload, searchResponse);
                } else if(apiName.equals(websocketApiConnection.getName())) {
                    response = websocketApiConnection.loadDataAndSaveToResponse(payload, searchResponse);
                }

                if(response != null && response.isError())
                    throw new ApiErrorException(response.getErrorMessage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return searchResponse;
    }
}
