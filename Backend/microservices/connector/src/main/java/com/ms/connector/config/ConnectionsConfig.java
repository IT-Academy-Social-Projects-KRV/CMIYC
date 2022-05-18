package com.ms.connector.config;

import com.ms.connector.service.api.ApiConnection;
import com.ms.connector.service.api.RestApiConnection;
import com.ms.connector.service.api.SoapApiConnection;
import com.ms.connector.service.api.WebsocketApiConnection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.jsoup.Connection;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "api")
public class ConnectionsConfig {

    private RestApiConnectionBuilder rest;
    private SoapApiConnectionBuilder soap;
    private WebsocketApiConnectionBuilder websocket;

    @Getter
    @Setter
    @ToString
    @NoArgsConstructor
    public static abstract class ApiConnectionBuilder {
        protected String path;
        protected String method = "POST";
        protected int timeout = 10000;

        public abstract ApiConnection build();
    }

    public static class RestApiConnectionBuilder extends ApiConnectionBuilder {
        @Override
        public RestApiConnection build() {
            return new RestApiConnection(path, Connection.Method.valueOf(method.toUpperCase()));
        }
    }

    public static class SoapApiConnectionBuilder extends ApiConnectionBuilder {
        @Override
        public SoapApiConnection build() {
            return new SoapApiConnection(path, Connection.Method.valueOf(method.toUpperCase()));
        }
    }

    public static class WebsocketApiConnectionBuilder extends ApiConnectionBuilder {
        @Override
        public WebsocketApiConnection build() {
            return new WebsocketApiConnection(path, timeout);
        }
    }
}


