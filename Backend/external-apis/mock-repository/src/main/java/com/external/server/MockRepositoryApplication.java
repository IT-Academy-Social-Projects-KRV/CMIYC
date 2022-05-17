package com.external.server;

import com.external.dto.Name;
import com.external.utils.MapperUtils;
import com.fasterxml.jackson.core.JsonProcessingException;

public class MockRepositoryApplication {

    public static void main(String[] args) throws JsonProcessingException {
        String ex = "{\"first\":\"Michelle\",\"middle\":\"\",\"last\":\"Martinez\",\"suffix\":\"\"}";
        MapperUtils.objectMapper.readValue(ex, Name.class);
        System.out.println(MapperUtils.objectMapper.readValue(ex, Name.class));
        int port = 9000;
        Server ws = new Server(port);
        ws.start();
        System.out.println("Server started on port: " + ws.getPort());
    }

}
