package com.mkyong.core.controller.searchControllerTests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;

import com.ms.search.service.ConnectorConnect;
import com.ms.search.service.DataConnect;
import com.ms.search.controller.SearchController;
import com.customstarter.model.request.SearchRequest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.HashSet;
import java.util.Set;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

/**
 * Authority App should work when you start this tests (because requests will check token)
 * **/
@WebMvcTest(SearchController.class)
@ContextConfiguration(classes = SearchController.class)

public class SearchControllerIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DataConnect dataConnect;

    @MockBean
    private ConnectorConnect connectorConnect;

    String authorizationHeader;
    String requestJson;

    @BeforeEach
    void setUp() throws JsonProcessingException {
        authorizationHeader = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6ImtleS1pZCJ9.eyJ1c2VyX25hbWUiOiJ1c2VyQGdtYWlsLmNvbSIsInNjb3BlIjoidXNlciIsImZ1bGxOYW1lIjoiVXNlciBGb28iLCJleHAiOjE2ODIxNjczNTksImF1dGhvcml0aWVzIjpbInVzZXIiXSwianRpIjoiYzFjNjg3NGItOTdmMi00NDVkLWJkYzUtMzFkZGIxNTU1MDM0IiwiZW1haWwiOiJ1c2VyQGdtYWlsLmNvbSIsImNsaWVudF9pZCI6ImNsaWVudC11aSJ9.V9sCrn8jCXoGLHtFUFjX_SauhDCKYxR0q57hWtNxJex_S9zUb10ZbVXTlLbI3ws2e-xZoJ33xnRBemLYvd9LJDIUQDf1-446wTkvFnCvzQjEtCIv3QTnrk0Jks1hz5n8U5NCwsoOwS9V6TCyvfjr4IGVXWsqSYlYYKCjiFXVODTLb2_UtqNUANayxFBQt-VG_qEGmABhmjeYvwTEoBjFwDg_kvdFhVU6oVVpIkFXQKj0r1REX8D23vlXbuVKI7P90CISigX6Y4QN11lMBLUWlD-nEQluUZnt7OLcNB0KAQt_NrFh-1OJxzB_T36cLy-GKWSkbHV4GI8tO9OcHhChuw";

        Set<String> apis = new HashSet<>();
        apis.add("api1");
        apis.add("api2");
        apis.add("api3");

        SearchRequest request = new SearchRequest();

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        requestJson = ow.writeValueAsString(request);
    }

    @Test
    public void getSearchAPI_checkIfDataFromDataIsJsonAndIfStatusOk_ResponseReturned() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/search")
                                .header("authorization", "Bearer " + authorizationHeader))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andReturn();
    }

    @Test
    public void postSearchAPI_checkIfDataFromConnectorIsJsonAndIfStatusOk_ResponseReturned() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/search")
                        .content(requestJson).contentType(MediaType.APPLICATION_JSON)
                        .header("authorization", "Bearer " + authorizationHeader))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
    }

}
