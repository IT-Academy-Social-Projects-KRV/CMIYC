package com.ms.connector.service.api;

import com.ms.connector.service.api.converter.BodyConverter;
import com.ms.connector.service.api.converter.SoapBodyConverter;
import com.ms.connector.service.client.SoapClient;
import org.jsoup.Connection;

public class SoapApiConnection extends HttpApiConnection {

    private static final BodyConverter converter = new SoapBodyConverter();

    public SoapApiConnection(String name, String path, Connection.Method method) {
        super(method, name, new SoapClient(path), converter);
    }

    @Override
    public ApiConnection.Type getType() {
        return ApiConnection.Type.SOAP;
    }
}
