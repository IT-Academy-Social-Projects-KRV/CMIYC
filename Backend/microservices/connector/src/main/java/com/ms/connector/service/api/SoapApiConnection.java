package com.ms.connector.service.api;

import com.customstarter.model.request.SearchRequestPayload;
import com.customstarter.model.response.SearchResponse;
import com.ms.connector.dto.response.ApiResponse;
import com.ms.connector.dto.response.SoapApiResponse;
import com.ms.connector.service.api.converter.SoapBodyConverter;
import com.ms.connector.service.client.SoapClient;
import org.jsoup.Connection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SoapApiConnection implements ApiConnection {

    private static final SoapBodyConverter converter = new SoapBodyConverter();

    private final String name;
    private final Connection.Method method;
    private final SoapClient client;

    public SoapApiConnection(
            @Value("${api.soap.name}") String name,
            @Value("${api.soap.path}") String path,
            @Value("${api.soap.method}") String method
    ) {
        this.name = name;
        this.method = Connection.Method.valueOf(method.toUpperCase());
        this.client = new SoapClient(path);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public ApiConnection.Type getType() {
        return ApiConnection.Type.SOAP;
    }

    @Override
    public SoapApiResponse loadData(SearchRequestPayload payload) {
        String requestBody = converter.payloadToBody(payload);
        String response = client.request(method, "", requestBody);

        return converter.responseBodyToObject(response);
    }

    @Override
    public SoapApiResponse loadDataAndSaveToResponse(SearchRequestPayload payload, SearchResponse response) {
        SoapApiResponse soapApiResponse = loadData(payload);
        response.setApi2Responses(soapApiResponse.getData());

        return soapApiResponse;
    }
}
