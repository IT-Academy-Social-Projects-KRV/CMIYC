package com.mkyong.core.controller.searchControllerTests;

import com.ms.search.service.ConnectorConnect;
import com.ms.search.service.DataConnect;
import com.ms.search.controller.SearchController;
import com.customstarter.model.request.SearchRequest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.xml.bind.JAXBException;

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

    SearchRequest searchRequest;
    String authorizationHeader;

    @BeforeEach
    void setUp() {
        authorizationHeader = "hi1";
        searchRequest = new SearchRequest();
    }


    @Test
    public void getSchemasSearchAPI_checkIfDataFromDataIsNotNull_BooleanReturned() throws JAXBException {
        assertThat(controller.getSchema(authorizationHeader)).isNotNull();
    }

    @Test
    public void searchSearchAPI_checkIfDataFromConnectorIsNotNull_BooleanReturned() {
        assertThat(controller.search(authorizationHeader, searchRequest, null)).isNotNull();
    }


}
