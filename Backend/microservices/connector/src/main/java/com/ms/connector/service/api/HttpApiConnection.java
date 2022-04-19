package com.ms.connector.service.api;

import com.ms.connector.model.SearchQuery;
import com.ms.connector.service.client.HttpClient;
import org.jsoup.Connection;

import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class HttpApiConnection extends ApiConnection {

    protected final Connection.Method method;

    public HttpApiConnection(Connection.Method method, String path) {
        super(path);
        this.method = method;
    }

    protected String readQueryToUrlString(SearchQuery query) {
        Map<String, String> data = convertQueryToMap(query);

        return data.entrySet()
                .stream()
                .map(entry -> entry.getKey() + "=" + URLEncoder.encode(entry.getValue(), Charset.defaultCharset()))
                .collect(Collectors.joining("&"));
    }

    protected Map<String, String> convertQueryToMap(SearchQuery query) {
        Map<String, String> data = new HashMap<>();
        data.put("firstName", query.getFirstName());
        data.put("lastName", query.getLastName());
        data.put("birthDayDate", query.getBirthDayDate());
        data.put("sex", query.getSex());
        while (data.values().remove(null));

        return data;
    }

    @Override
    public final Object getData(SearchQuery query) throws Exception {
        String result;
        if(method.hasBody()) {
            String requestBody = prepareRequestBody(query);
            result = getClient().request(method, "", requestBody);
        } else {
            String queryString = "?" + readQueryToUrlString(query);
            result = getClient().request(method, queryString, null);
        }

        return result;
    }

    protected abstract HttpClient getClient();

    protected abstract String prepareRequestBody(SearchQuery query);
}
