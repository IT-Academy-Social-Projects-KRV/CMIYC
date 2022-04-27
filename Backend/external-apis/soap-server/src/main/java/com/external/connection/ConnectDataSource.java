package com.external.connection;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

public class ConnectDataSource extends WebSocketClient {

    private String answer;

    public ConnectDataSource(URI serverUri) throws InterruptedException {
        super(serverUri);
        connect();
        Thread.sleep(1000);
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        System.out.println("connected :" + getURI());
    }

    @Override
    public void onMessage(String s) {
        answer = s;
    }

    @Override
    public void onClose(int i, String s, boolean b) {

    }

    @Override
    public void onError(Exception e) {

    }

    public String getAnswer() {
        return answer;
    }

}
