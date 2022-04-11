package com.external.server;

import java.net.UnknownHostException;

public class Main {
    public static void main(String[] args) throws UnknownHostException {
        int port = 9000;
        Server ws = new Server(port);
        ws.start();
        System.out.println("Server started on port: " + ws.getPort());
    }
}
