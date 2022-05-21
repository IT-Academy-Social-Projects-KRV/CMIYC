package com.external;

import com.external.dto.API;
import com.external.dto.SearchRequest;
import com.external.dto.SearchResponse;
import com.external.entity.Person;
import com.external.helpers.MockedWebsocketClient;
import com.external.repository.PersonRepository;
import com.external.server.Server;
import com.external.utils.MapperUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ServerUnitTests {

    static Server server;
    static MockedWebsocketClient client;
    static ObjectMapper objectMapper = MapperUtils.objectMapper;
    static String requestPayload;

    @SneakyThrows
    @BeforeAll
    static void init() {
        server = new Server(10000);
        client = new MockedWebsocketClient(URI.create("ws://localhost:10000"));

        PersonRepository personRepository = new PersonRepository();
        Person firstPerson = personRepository.getAll().get(0);
        requestPayload = objectMapper.writeValueAsString(firstPerson);
    }

    @SneakyThrows
    static SearchResponse getResponse(String request) {
        server.process(request, client);

        String response = client.getLastSend();
        return objectMapper.readValue(response, SearchResponse.class);
    }

    @Test
    public void server_processMessageWithUnknownApi_errorReturned() {
        String request = "SomeUnnecessaryLongNameForAPI" + SearchRequest.REQUEST_SPLIT + requestPayload;
        SearchResponse searchResponse = getResponse(request);

        assertTrue(searchResponse.isError(), "Error must be returned if unknown API is requested");
    }

    @Test
    public void server_processMessageWithoutAPI_errorReturned() {
        SearchResponse searchResponse = getResponse(requestPayload);

        assertTrue(searchResponse.isError(), "Error must be returned if API was not specified");
    }

    @Test
    public void server_processMessageWithBadName_emptyListReturned() {
        String request = API.API1.getName() + SearchRequest.REQUEST_SPLIT + "{\"name\":{\"first\":\"SomeUnnecessaryLongNameThatWillNeverBeFound\"}}";
        SearchResponse searchResponse = getResponse(request);

        assertFalse(searchResponse.isError(), "If no person is found, empty list must be returned instead of error");
        assertTrue(searchResponse.getData().isEmpty(), "Returned list must be empty");
    }
}
