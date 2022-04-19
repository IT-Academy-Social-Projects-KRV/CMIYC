package com.ms.connector.service;

import com.ms.connector.model.SearchQuery;
import com.ms.connector.service.api.ApiConnection;
import com.ms.connector.service.api.RestApiConnection;
import com.ms.connector.service.api.SoapApiConnection;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.xml.soap.*;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import static org.jsoup.Connection.Method.GET;
import static org.jsoup.Connection.Method.POST;

@Service
public class APIService {

    private final Map<String, ApiConnection> apis = new HashMap<>();

    @PostConstruct
    public void init() throws Exception {
        ApiConnection restPostConnection = new RestApiConnection(POST, "http://localhost:9001/rest");
        ApiConnection restGetConnection = new RestApiConnection(GET, "http://localhost:9001/rest");

        ApiConnection soapConnection = new SoapApiConnection(POST, "http://localhost:9003/soap");

        SearchQuery query = new SearchQuery();
        query.setFirstName("Steven");
        query.setLastName("Stevenson");
        query.setBirthDayDate("03.03.1983");

        System.out.println(soapConnection.getData(query));
        //System.out.println(restPostConnection.getData(query));
    }

}
