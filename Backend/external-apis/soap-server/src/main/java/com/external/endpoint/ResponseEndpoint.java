package com.external.endpoint;

import api.soap.xsd.Payload;
import api.soap.xsd.SearchResponse;
import com.external.connection.ConnectDataSource;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;

@Endpoint
public class ResponseEndpoint {

    private static final String NAMESPACE_URI = "http://soap.api/xsd";
    private static final String DATA_HOST = Objects.requireNonNullElse(
            System.getenv("ROUTES_EXTERNAL_MOCK_REPOSITORY"),
            "ws://localhost:9000"
    );

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "Payload")
    @ResponsePayload
    public SearchResponse getPerson(@RequestPayload Payload request)
            throws InterruptedException, JsonProcessingException, URISyntaxException {
        ObjectMapper mapper = new ObjectMapper();
        ConnectDataSource connection = new ConnectDataSource(new URI(DATA_HOST));
        connection.send("api2_" + mapper.writeValueAsString(request));
        Thread.sleep(500);

        System.out.println("Answer: " + connection.getAnswer());

        return mapper.readValue(connection.getAnswer(), SearchResponse.class);
    }

}
