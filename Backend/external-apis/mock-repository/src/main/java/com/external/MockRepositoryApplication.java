package com.external;

import com.external.server.Server;

public class MockRepositoryApplication {

    public static void main(String[] args) {
        int port = 9000;
        Server ws = new Server(port);
        ws.start();
        System.out.println("Server started on port: " + ws.getPort());
    }

}
