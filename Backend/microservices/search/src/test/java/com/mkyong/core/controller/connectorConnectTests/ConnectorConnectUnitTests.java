package com.mkyong.core.controller.connectorConnectTests;

import com.ms.search.connectInterface.ConnectorConnect;
import com.ms.search.model.SearchQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = ConnectorConnect.class)
public class ConnectorConnectUnitTests {

    @MockBean
    private ConnectorConnect connectorConnect;

    SearchQuery searchQuery;
    String authorizationHeader;
    final Map<String, Object> response = new HashMap<>();

    @BeforeEach
    void setUp() {
        searchQuery = new SearchQuery("name", "lastName", "birthDate", "sex", Collections.singleton("api1"));

        authorizationHeader = "hi1";

        Map<String, String> api1Response = new HashMap<>();
        api1Response.put("middle", "Aristarchus");
        api1Response.put("email", "test@gmail.com");
        response.put("api1", api1Response);
    }

    @Test
    public void searcher_getDataFromConnector_MapReturned() {
        when(connectorConnect.searcher(authorizationHeader, searchQuery)).thenReturn(response);
        assertThat(connectorConnect.searcher(authorizationHeader, searchQuery)).isEqualTo(response);
    }

}
