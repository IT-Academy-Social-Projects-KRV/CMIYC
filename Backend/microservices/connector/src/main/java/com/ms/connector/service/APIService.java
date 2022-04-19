package com.ms.connector.service;

import com.ms.connector.model.SearchQuery;
import com.ms.connector.service.api.APIConnection;
import com.ms.connector.service.api.RestAPIConnection;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

import static org.jsoup.Connection.Method.GET;
import static org.jsoup.Connection.Method.POST;

@Service
public class APIService {

    private final Map<String, APIConnection> apis = new HashMap<>();

    @PostConstruct
    public void init() throws Exception {
        APIConnection restPostConnection = new RestAPIConnection(POST, "http://localhost:9001/rest");
        APIConnection restGetConnection = new RestAPIConnection(GET, "http://localhost:9001/rest");

        SearchQuery query = new SearchQuery();
        query.setFirstName("Ivan");
        query.setLastName("Ivanov");

        System.out.println(restGetConnection.getData(query));
        System.out.println(restPostConnection.getData(query));
    }

}
