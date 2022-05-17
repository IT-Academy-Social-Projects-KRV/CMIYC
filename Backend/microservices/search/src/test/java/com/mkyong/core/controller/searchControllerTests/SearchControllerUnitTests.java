package com.mkyong.core.controller.searchControllerTests;

import com.ms.search.connectInterface.ConnectorConnect;
import com.ms.search.connectInterface.DataConnect;
import com.ms.search.controller.SearchController;
import com.ms.search.model.SearchQuery;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.xml.bind.JAXBException;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(classes = SearchController.class)
@ExtendWith(SpringExtension.class)
public class SearchControllerUnitTests {

    @MockBean
    private DataConnect dataConnect;

    @MockBean
    private ConnectorConnect connectorConnect;

    @Autowired
    private SearchController controller;

    SearchQuery searchQuery;
    String authorizationHeader;

    @BeforeEach
    void setUp() {
        authorizationHeader = "hi1";
        searchQuery = new SearchQuery("name", "lastName", "birthDate", "sex", Collections.singleton("Api1"));
    }


    @Test
    public void getSchemasSearchAPI_checkIfDataFromDataIsNotNull_BooleanReturned() throws JAXBException {
        assertThat(controller.getSchema(authorizationHeader)).isNotNull();
    }

    @Test
    public void searchSearchAPI_checkIfDataFromConnectorIsNotNull_BooleanReturned() {
        assertThat(controller.search(authorizationHeader, searchQuery)).isNotNull();
    }


}
