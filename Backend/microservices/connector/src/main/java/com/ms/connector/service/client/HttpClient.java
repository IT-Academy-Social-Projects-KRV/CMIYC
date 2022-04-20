package com.ms.connector.service.client;

import lombok.SneakyThrows;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.util.Objects;

public abstract class HttpClient extends AbstractClient {

    public HttpClient(String baseUrl, String authorizationHeader) {
        super(baseUrl, authorizationHeader);
    }

    public String getRequest(String url) {
        return request(Connection.Method.GET, url, null);
    }

    public String postRequest(String url, String requestBody) {
        return request(Connection.Method.POST, url, requestBody);
    }

    public String putRequest(String url, String requestBody) {
        return request(Connection.Method.PUT, url, requestBody);
    }

    public String deleteRequest(String url) {
        return request(Connection.Method.DELETE, url, null);
    }

    @SneakyThrows
    public String request(Connection.Method method, String queryString, String requestBody) {
        Connection connection = prepareRequest(method, queryString);
        if(requestBody != null)
            connection.requestBody(requestBody);

        Connection.Response response = connection.execute();
        return response.body();
    }

    protected Connection prepareRequest(Connection.Method method, String url) {
        Objects.requireNonNull(url);

        return Jsoup
                .connect(getBaseUrl() + url)
                .method(method)
                .header("Authorization", getAuthorization())
                .ignoreContentType(true);
    }
}
