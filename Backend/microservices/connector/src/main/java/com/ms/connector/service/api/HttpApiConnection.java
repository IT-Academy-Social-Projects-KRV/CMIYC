package com.ms.connector.service.api;

import com.ms.connector.dto.SearchQuery;
import com.ms.connector.dto.SearchResponse;
import com.ms.connector.service.api.converter.BodyConverter;
import com.ms.connector.service.client.HttpClient;
import org.jsoup.Connection;

import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class HttpApiConnection implements ApiConnection {

    private final Connection.Method method;
    private final String name;
    private final HttpClient client;
    private final BodyConverter converter;

    public HttpApiConnection(Connection.Method method, String name, HttpClient client, BodyConverter converter) {
        this.method = method;
        this.name = name;
        this.client = client;
        this.converter = converter;
    }

    protected String readQueryToUrlString(SearchQuery query) {
        Map<String, String> data = query.toMap();

        return data.entrySet()
                .stream()
                .map(entry -> entry.getKey() + "=" + URLEncoder.encode(entry.getValue(), Charset.defaultCharset()))
                .collect(Collectors.joining("&"));
    }

    @Override
    public final SearchResponse getData(SearchQuery query) {
        String response;
        if(method.hasBody()) {
            String requestBody = converter.queryToRequestBody(query);
            response = client.request(method, "", requestBody);
        } else {
            String queryString = "?" + readQueryToUrlString(query);
            response = client.request(method, queryString, null);
        }

        return converter.responseBodyToObject(response);
    }

    @Override
    public final String getName() {
        return name;
    }
}
