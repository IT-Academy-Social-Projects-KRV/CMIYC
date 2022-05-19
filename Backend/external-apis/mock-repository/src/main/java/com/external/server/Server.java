package com.external.server;

import com.customstarter.model.response.Response;
import com.external.dto.API;
import com.external.dto.SearchRequest;
import com.external.dto.SearchResponse;
import com.external.entity.Person;
import com.external.repository.PersonRepository;
import com.external.utils.MapperUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.stream.Collectors;

public class Server extends WebSocketServer {

    private final PersonRepository personRepository = new PersonRepository();

    public Server(int port) {
        super(new InetSocketAddress(port));
    }

    @Override
    public void onOpen(WebSocket webSocket, ClientHandshake clientHandshake) {
        System.out.println(getHostAddress(webSocket) + " connected to server");
    }

    @Override
    public void onClose(WebSocket webSocket, int i, String s, boolean b) {
        System.out.println(getHostAddress(webSocket) + " has disconnected from server!");
    }

    @Override
    public void onMessage(WebSocket webSocket, String s) {
        try {
            process(s, webSocket);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } finally {
            webSocket.close();
        }
    }

    @Override
    public void onError(WebSocket webSocket, Exception e) {

    }

    @Override
    public void onStart() {
        System.out.println("Server started!");
        setConnectionLostTimeout(0);
        setConnectionLostTimeout(100);
    }

    public void process(String message, WebSocket webSocket) throws JsonProcessingException {
        SearchResponse result;

        try {
            SearchRequest request = SearchRequest.fromMessage(message);
            List<Person> people = personRepository.findAllByRequest(request.getRequestPayload());

            API api = request.getApi();
            List<Response> personDataList = people.stream()
                    .map(person -> api.createPersonData(person, request.getRequestPayload()))
                    .collect(Collectors.toList());

            result = SearchResponse.success(personDataList);
        } catch (Exception e) {
            result = SearchResponse.error(e.getMessage());
        }

        String json = MapperUtils.objectMapper.writeValueAsString(result);
        webSocket.send(json);
    }

    private String getHostAddress(WebSocket webSocket) {
        return webSocket.getRemoteSocketAddress()
                .getAddress()
                .getHostAddress();
    }

}
