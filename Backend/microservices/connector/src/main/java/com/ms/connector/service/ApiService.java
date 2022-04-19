package com.ms.connector.service;

import com.ms.connector.config.ConnectionsConfig;
import com.ms.connector.dto.SearchQuery;
import com.ms.connector.service.api.ApiConnection;
import com.ms.connector.service.api.RestApiConnection;
import com.ms.connector.service.api.SoapApiConnection;
import com.ms.connector.service.api.WebsocketApiConnection;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

import static org.jsoup.Connection.Method.GET;
import static org.jsoup.Connection.Method.POST;

@Service
@AllArgsConstructor
public class ApiService {

    private final Map<String, ApiConnection> apis = new HashMap<>();

    private ConnectionsConfig connectionsConfig;

    @PostConstruct
    public void initApiConnections() throws Exception {
        connectionsConfig
                .getConnections()
                .values()
                .forEach(apiConnectionData -> {
                    ApiConnection connection = apiConnectionData.buildApiConnection();
                    apis.put(connection.getName(), connection);
                });
    }


    public Map<String, Object> processRequest(SearchQuery searchQuery) {
        Map<String, Object> result = new HashMap<>();

        for (String apiName : searchQuery.getForeignDataSource()) {
            ApiConnection apiConnection = apis.get(apiName);
            if(apiConnection != null) {
                try {
                    result.put(apiName, apiConnection.getData(searchQuery));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return result;
    }
}
