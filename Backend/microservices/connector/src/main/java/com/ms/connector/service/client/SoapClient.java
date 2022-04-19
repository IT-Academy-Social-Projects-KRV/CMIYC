package com.ms.connector.service.client;

import org.jsoup.Connection;

public class SoapClient extends HttpClient {

    private final String contentTypeHeader;

    public SoapClient(String baseUrl) {
        this(baseUrl, null);
    }

    public SoapClient(String baseUrl, String authorizationHeader) {
        this(baseUrl, authorizationHeader, "text/xml");
    }

    public SoapClient(String baseUrl, String authorizationHeader, String contentTypeHeader) {
        super(baseUrl, authorizationHeader);
        this.contentTypeHeader = contentTypeHeader;
    }

    @Override
    protected Connection prepareRequest(Connection.Method method, String url) {
        return super
                .prepareRequest(method, url)
                .header("Content-type", contentTypeHeader);
    }
}
