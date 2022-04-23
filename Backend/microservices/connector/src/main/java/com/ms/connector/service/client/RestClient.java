package com.ms.connector.service.client;

import lombok.AccessLevel;
import lombok.Getter;
import org.jsoup.Connection;

@Getter(AccessLevel.PROTECTED)
public class RestClient extends HttpClient {

    public RestClient(String url) {
        this(url, null);
    }

    public RestClient(String baseUrl, String authorizationHeader) {
        super(baseUrl, authorizationHeader);
    }

    @Override
    protected Connection prepareRequest(Connection.Method method, String url) {
        return super
                .prepareRequest(method, url)
                .header("Content-type", "application/json");
    }
}
