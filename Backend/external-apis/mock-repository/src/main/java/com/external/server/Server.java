package com.external.server;

import com.external.dto.ApiOneReq;
import com.external.dto.ApiThreeReq;
import com.external.dto.ApiTwoReq;
import com.external.dto.PersonRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.java_websocket.WebSocket;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.Collections;

public class Server extends WebSocketServer {

    private static final ObjectMapper mapper = new ObjectMapper();
    static {
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    private PersonRepository pr = new PersonRepository();

    public Server(int port) throws UnknownHostException {
        super(new InetSocketAddress(port));
    }

    public Server(InetSocketAddress address) {
        super(address);
    }

    public Server(int port, Draft_6455 draft) {
        super(new InetSocketAddress(port), Collections.<Draft>singletonList(draft));
    }

    @Override
    public void onOpen(WebSocket webSocket, ClientHandshake clientHandshake) {
        System.out.println(
                webSocket.getRemoteSocketAddress().getAddress().getHostAddress() + " connected to server");
    }

    @Override
    public void onClose(WebSocket webSocket, int i, String s, boolean b) {
        System.out.println(webSocket + " has disconnected from server!");
    }

    @Override
    public void onMessage(WebSocket webSocket, String s) {
        try {
            process(s, webSocket);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }finally {
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

    public String getApi(String s) {
        return s.substring(0, 4);
    }

    public String getMessage(String s) {
        return s.substring(5);
    }

    public void process(String s, WebSocket ws) throws JsonProcessingException {
        if (getApi(s).equals("api1")) {
            ApiOneReq apiOneReq = mapper.readValue(getMessage(s), ApiOneReq.class);
            ws.send(new Gson().toJson(pr.findPerson(apiOneReq)));
        }

        if (getApi(s).equals("api2")) {
            ApiTwoReq apiTwoReq = mapper.readValue(getMessage(s), ApiTwoReq.class);
            ws.send(new Gson().toJson(pr.findPerson(apiTwoReq)));
        }

        if (getApi(s).equals("api3")) {
            ApiThreeReq apiThreeReq = mapper.readValue(getMessage(s), ApiThreeReq.class);
            ws.send(new Gson().toJson(pr.findPerson(apiThreeReq)));
        }
    }
}
