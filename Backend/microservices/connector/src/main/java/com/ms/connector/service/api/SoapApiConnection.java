package com.ms.connector.service.api;

import com.ms.connector.service.api.converter.BodyConverter;
import com.ms.connector.service.api.converter.SoapBodyConverter;
import com.ms.connector.service.client.HttpClient;
import com.ms.connector.service.client.SoapClient;
import org.jsoup.Connection;

public class SoapApiConnection extends HttpApiConnection {

    private static final BodyConverter converter = new SoapBodyConverter();

    private final String name;
    private final SoapClient client;

    public SoapApiConnection(String name, String path, Connection.Method method) {
        super(method);
        this.name = name;
        this.client = new SoapClient(path);
    }

    @Override
    protected HttpClient getClient() {
        return client;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public ApiConnectionType getType() {
        return ApiConnectionType.SOAP;
    }

    @Override
    public BodyConverter getBodyConverter() {
        return converter;
    }
}
