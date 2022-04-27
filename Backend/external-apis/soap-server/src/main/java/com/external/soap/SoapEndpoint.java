package com.external.soap;

import api.soap.xsd.GetPersonRequest;
import api.soap.xsd.GetPersonResponse;
import api.soap.xsd.Person;
import com.external.connection.ConnectDataSource;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;

@Endpoint
public class SoapEndpoint {

    private static final String NAMESPACE_URI = "http://soap.api/xsd";
    private static final String DATA_HOST = Objects.requireNonNullElse(
            System.getenv("ROUTES_EXTERNAL_MOCK_REPOSITORY"),
            "ws://localhost:9000"
    );

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getPersonRequest")
    @ResponsePayload
    public GetPersonResponse getPerson(@RequestPayload GetPersonRequest request)
            throws InterruptedException, JsonProcessingException, URISyntaxException {
        ConnectDataSource connection = new ConnectDataSource(new URI(DATA_HOST));
        ObjectMapper mapper = new ObjectMapper();
        connection.send("api2_" + new Gson().toJson(request));
        Thread.sleep(500);
        Person answer = mapper.readValue(connection.getAnswer(), Person.class);
        GetPersonResponse response = new GetPersonResponse();
        response.setPerson(answer);

        return response;
    }

}
