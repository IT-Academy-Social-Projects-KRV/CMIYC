package com.external.websocketserver;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

@ServerEndpoint(value = "/ws")
public class WebsocketEndpoint {
    private Session session;
    @OnOpen
    public void onOpen(Session session) throws IOException {
        this.session = session;
        this.session.getBasicRemote().sendText("Welcome to WebSocket server");
    }

    @OnMessage
    public void onMessage(Session session, String message) throws IOException {

    }

    @OnClose
    public void onClose(Session session) throws IOException {

    }

    @OnError
    public void onError (){}
}
