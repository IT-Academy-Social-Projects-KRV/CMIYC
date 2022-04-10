package com.external.soap;

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

@Endpoint
public class SoapEndpoint {
    private static final String NAMESPACE_URI = "http://soap.api/xsd";
    private ConnectDataSource connection = new ConnectDataSource (new URI("ws://localhost:9000"));


    public SoapEndpoint() throws URISyntaxException, InterruptedException {
    }


    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getPersonRequest")
    @ResponsePayload
    public GetPersonResponse getPerson(@RequestPayload GetPersonRequest request) throws InterruptedException, JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        connection.send("api2_"+new Gson().toJson(request));
        Thread.sleep(1500);
        Person a = mapper.readValue(c.answer,Person.class);
        GetPersonResponse response = new GetPersonResponse();
        response.setPerson(a);

        return response;
    }
}
