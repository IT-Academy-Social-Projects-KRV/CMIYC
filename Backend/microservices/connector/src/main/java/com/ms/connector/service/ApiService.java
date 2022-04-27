package com.ms.connector.service;

import com.ms.connector.config.ConnectionsConfig;
import com.ms.connector.dto.SearchQuery;
import com.ms.connector.dto.SearchResponse;
import com.ms.connector.service.api.ApiConnection;
import com.ms.connector.service.api.converter.SoapBodyConverter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class ApiService {

    private final Map<String, ApiConnection> apis = new HashMap<>();

    private ConnectionsConfig connectionsConfig;

    @PostConstruct
    public void initApiConnections() {
        connectionsConfig.getConnections()
                .values()
                .stream()
                .map(ConnectionsConfig.ApiConnectionData::buildApiConnection)
                .forEach(connection -> apis.put(connection.getName(), connection));
    }

    public Map<String, SearchResponse> handleSearchRequest(SearchQuery searchQuery) {
        Map<String, SearchResponse> result = new HashMap<>();

        for (String apiName : searchQuery.getForeignDataSource()) {
            ApiConnection apiConnection = apis.get(apiName);
            if (apiConnection != null) {
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
