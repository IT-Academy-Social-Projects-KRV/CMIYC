package com.external.connection;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.SocketTimeoutException;
import java.net.URI;

public class ConnectDataSource extends WebSocketClient {

    private String answer;

    public ConnectDataSource(URI serverUri) throws InterruptedException {
        super(serverUri);
        connectBlocking();
    }

    public void waitForResponse() throws SocketTimeoutException {
        try {
            for (int i = 0; i < 50; i++) {
                Thread.sleep(100);
                if(answer != null)
                    return;
            }

            throw new SocketTimeoutException("There was no response from socket server");
        } catch (InterruptedException ignored) {}
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        System.out.println("connected: " + getURI());
    }

    @Override
    public void onMessage(String message) {
        System.out.println("Message received: " + message);
        answer = message;
    }

    @Override
    public void onClose(int i, String s, boolean b) {

    }

    @Override
    public void onError(Exception e) {

    }

    public String getAnswer () {
        return answer;
    }
}
