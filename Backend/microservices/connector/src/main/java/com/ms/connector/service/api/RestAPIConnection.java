package com.ms.connector.service.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ms.connector.model.SearchQuery;
import com.ms.connector.service.client.RestClient;
import org.jsoup.Connection;

import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class RestAPIConnection extends HttpAPIConnection {

    private final ObjectMapper mapper = new ObjectMapper();
    private final RestClient client;

    public RestAPIConnection(Connection.Method method, String path) {
        super(method, path);
        this.client = new RestClient(path);
    }

    @Override
    public Object getData(SearchQuery query) throws JsonProcessingException {
        String result;
        if(method.hasBody()) {
            String requestBody = mapper.writeValueAsString(query);
            result = client.request(method, "", requestBody);
        } else {
            String queryString = "?" + readQueryToUrlString(query);
            result = client.request(method, queryString, null);
        }

        return result;
    }

    private String readQueryToUrlString(SearchQuery query) {
        Map<String, String> data = new HashMap<>();
        data.put("firstName", query.getFirstName());
        data.put("lastName", query.getLastName());
        data.put("birthDayDate", query.getBirthDayDate());
        data.put("sex", query.getSex());

        return data.entrySet()
                .stream()
                .filter(entry -> entry.getValue() != null)
                .map(entry -> entry.getKey() + "=" + URLEncoder.encode(entry.getValue(), Charset.defaultCharset()))
                .collect(Collectors.joining("&"));
    }
}
