package com.external.websocketserver;

import com.external.connection.ConnectDataSource;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;

@ServerEndpoint(value = "/ws")
public class WebsocketEndpoint {

    private static final String DATA_HOST = Objects.requireNonNullElse(
            System.getenv("ROUTES_EXTERNAL_MOCK_REPOSITORY"),
            "ws://localhost:9000"
    );

    private Session session;
    private ConnectDataSource connection;

    @OnOpen
    public void onOpen(Session session) throws IOException {
        this.session = session;
        this.session.getBasicRemote()
                .sendText("Welcome to WebSocket server");
    }

    @OnMessage
    public void onMessage(Session session, String message) throws IOException {
        try {
            connection = new ConnectDataSource(new URI(DATA_HOST));
            connection.send("api3_" + message);
            Thread.sleep(1000);
            this.session = session;
            this.session.getBasicRemote()
                    .sendText(connection.getAnswer());
        } catch (InterruptedException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @OnClose
    public void onClose(Session session) throws IOException {

    }

}
