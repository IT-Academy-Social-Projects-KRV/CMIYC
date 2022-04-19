package com.ms.connector.service.client;

import lombok.SneakyThrows;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.util.concurrent.TimeoutException;

public class WebsocketClient extends AbstractClient {

    private String lastMessage = null;
    private Exception lastException = null;

    private final int TIMEOUT = 10;
    // Check 10 times per second
    private final int TIMEOUT_STEP = 100;

    private WebSocketClient client;

    @SneakyThrows
    private void initClient() {
        if (this.client != null && this.client.isOpen()) return;

        this.client = new WebSocketClient(new URI(getBaseUrl())) {
            @Override
            public void onOpen(ServerHandshake serverHandshake) {
            }

            @Override
            public void onMessage(String s) {
                lastMessage = s;
            }

            @Override
            public void onClose(int i, String s, boolean b) {
            }

            @Override
            public void onError(Exception e) {
                lastException = e;
            }
        };

        this.client.connectBlocking();

        if(!this.client.isOpen())
            throw new RuntimeException("Client was unable to connect to websocket " + getBaseUrl());
    }

    public WebsocketClient(String baseUrl) {
        super(baseUrl, null);
    }

    public synchronized void send(String dataToSend) {
        initClient();

        lastMessage = null;
        lastException = null;

        client.send(dataToSend);
    }

    @SneakyThrows
    public synchronized String sendAndReceive(String dataToSend) {
        send(dataToSend);

        waitForResponseOrError();
        if(lastException != null)
            throw lastException;

        if(lastMessage == null)
            throw new TimeoutException("There was no answer from server");

        return lastMessage;
    }

    @SneakyThrows
    public void close() {
        if(client == null || client.isClosing() || client.isClosed())
            return;

        client.closeBlocking();
    }

    @SneakyThrows
    private void waitForResponseOrError() {
        int steps = TIMEOUT * 1000 / TIMEOUT_STEP;
        for (int i = 0; i < steps; i++) {
            Thread.sleep(TIMEOUT_STEP);
            if(lastMessage != null || lastException != null)
                return;
        }
    }
}
