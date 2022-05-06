package com.external.rest;

import com.external.connection.ConnectDataSource;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.*;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@WebServlet(name = "server", value = "/rest")
public class Server extends HttpServlet {

    private static final String DATA_HOST = Objects.requireNonNullElse(
            System.getenv("ROUTES_EXTERNAL_MOCK_REPOSITORY"),
            "ws://localhost:9000"
    );

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Map<String, Object> data = new HashMap<>();
        req.getParameterMap()
                .forEach((key, values) -> {
                    if (values == null || values.length < 2) {
                        String value = values == null || values.length == 0 ? "" : values[0];
                        data.put(key, value);
                    } else {
                        data.put(key, values);
                    }
                });

        String json = new ObjectMapper().writeValueAsString(data);

        handleRequest(json, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        handleRequest(getBody(req), resp);
    }

    private void handleRequest(String json, HttpServletResponse response) throws IOException {
        String answer;
        ConnectDataSource connection = null;

        try {
            connection = new ConnectDataSource(new URI(DATA_HOST));
            connection.send("api1_" + json);
            connection.waitForResponse();

            answer = connection.getAnswer();
        } catch (Exception e) {
            e.printStackTrace();
            throw new IOException(e.getMessage());
        } finally {
            try {
                if(connection != null) connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();
        out.print(answer);
        out.flush();
    }

    private String getBody(HttpServletRequest request) throws IOException {
        String body;
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = null;

        try {
            InputStream inputStream = request.getInputStream();
            if (inputStream != null) {
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                char[] charBuffer = new char[128];
                int bytesRead;
                while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                    stringBuilder.append(charBuffer, 0, bytesRead);
                }
            }
        } finally {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
        }

        body = stringBuilder.toString();
        return body;
    }

}
