package com.ms.connector.service.api;

import com.ms.connector.dto.SearchQuery;
import com.ms.connector.service.api.converter.BodyConverter;
import com.ms.connector.service.client.HttpClient;
import org.jsoup.Connection;

import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class HttpApiConnection implements ApiConnection {

    protected final Connection.Method method;

    public HttpApiConnection(Connection.Method method) {
        this.method = method;
    }

    protected String readQueryToUrlString(SearchQuery query) {
        Map<String, String> data = query.toMap();

        return data.entrySet()
                .stream()
                .map(entry -> entry.getKey() + "=" + URLEncoder.encode(entry.getValue(), Charset.defaultCharset()))
                .collect(Collectors.joining("&"));
    }

    @Override
    public final Object getData(SearchQuery query) {
        String response;
        if(method.hasBody()) {
            String requestBody = getBodyConverter().queryToRequestBody(query);
            response = getClient().request(method, "", requestBody);
        } else {
            String queryString = "?" + readQueryToUrlString(query);
            response = getClient().request(method, queryString, null);
        }

        return getBodyConverter().responseBodyToObject(response);
    }

    protected abstract HttpClient getClient();

    protected abstract BodyConverter getBodyConverter();
}
