package com.ms.connector.service;

import com.customstarter.model.request.SearchRequest;
import com.ms.connector.config.ConnectionsConfig;
import com.ms.connector.dto.SearchResponse;
import com.ms.connector.dto.apiresponse.ApiResponse;
import com.ms.connector.dto.apiresponse.RestApiResponse;
import com.ms.connector.dto.apiresponse.SoapApiResponse;
import com.ms.connector.dto.apiresponse.WebsocketApiResponse;
import com.ms.connector.exception.ApiErrorException;
import com.ms.connector.service.api.RestApiConnection;
import com.ms.connector.service.api.SoapApiConnection;
import com.ms.connector.service.api.WebsocketApiConnection;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class ApiService {

    private final RestApiConnection restApiConnection;
    private final SoapApiConnection soapApiConnection;
    private final WebsocketApiConnection websocketApiConnection;

    @Autowired
    public ApiService(ConnectionsConfig connectionsConfig) {
        this.restApiConnection = connectionsConfig.getRest().build();
        this.soapApiConnection = connectionsConfig.getSoap().build();
        this.websocketApiConnection = connectionsConfig.getWebsocket().build();
    }

    public SearchResponse handleSearchRequest(SearchRequest request) {
        SearchResponse result = new SearchResponse();

        for (String apiName : request.getApis()) {
            try {
                ApiResponse response = null;

                switch (apiName.toLowerCase()) {
                    case "api1": {
                        RestApiResponse restApiResponse = restApiConnection.getData(request.getData());
                        result.setApi1Responses(restApiResponse.getData());
                        response = restApiResponse;

                        break;
                    }
                    case "api2": {
                        SoapApiResponse soapApiResponse = soapApiConnection.getData(request.getData());
                        result.setApi2Responses(soapApiResponse.getData());
                        response = soapApiResponse;

                        break;

                    }
                    case "api3": {
                        WebsocketApiResponse websocketApiResponse = websocketApiConnection.getData(request.getData());
                        result.setApi3Responses(websocketApiResponse.getData());
                        response = websocketApiResponse;

                        break;
                    }
                }

                if(response != null && response.isError())
                    throw new ApiErrorException(response.getErrorMessage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return result;
    }
}
