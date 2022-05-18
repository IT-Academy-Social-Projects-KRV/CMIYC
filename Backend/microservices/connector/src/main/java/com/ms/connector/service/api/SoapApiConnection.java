package com.ms.connector.service.api;

import com.customstarter.model.request.SearchRequestPayload;
import com.ms.connector.dto.response.SoapApiResponse;
import com.ms.connector.service.api.converter.SoapBodyConverter;
import com.ms.connector.service.client.SoapClient;
import org.jsoup.Connection;

public class SoapApiConnection implements ApiConnection {

    private static final SoapBodyConverter converter = new SoapBodyConverter();

    private final Connection.Method method;
    private final SoapClient client;

    public SoapApiConnection(String path, Connection.Method method) {
        this.method = method;
        this.client = new SoapClient(path);
    }

    @Override
    public ApiConnection.Type getType() {
        return ApiConnection.Type.SOAP;
    }

    @Override
    public SoapApiResponse getData(SearchRequestPayload payload) throws Exception {
        String requestBody = converter.payloadToBody(payload);
        String response = client.request(method, "", requestBody);

        return converter.responseBodyToObject(response);
    }
}
