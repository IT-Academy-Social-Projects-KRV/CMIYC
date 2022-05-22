package com.external.helpers;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

public class MockedWebsocketClient extends WebSocketClient {

    private String lastSend;

    public MockedWebsocketClient(URI serverUri) {
        super(serverUri);
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {

    }

    @Override
    public void onMessage(String message) {

    }

    @Override
    public void onClose(int i, String s, boolean b) {

    }

    @Override
    public void onError(Exception e) {

    }

    @Override
    public void send(String text) {
        lastSend = text;
    }

    public String getLastSend() {
        return lastSend;
    }
}
