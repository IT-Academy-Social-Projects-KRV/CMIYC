package com.ms.connector.config;

import com.ms.connector.service.api.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.jsoup.Connection;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Getter
@Configuration
@ConfigurationProperties(prefix = "api")
public class ConnectionsConfig {

    private final Map<String, ApiConnectionData> connections = new HashMap<>();

    @Getter
    @Setter
    @ToString
    @NoArgsConstructor
    public static class ApiConnectionData {
        private String path;
        private String name;
        private String type;
        private String method = "POST";
        private int timeout = 5000;

        public void setType(String type) {
            this.type = type.toUpperCase();
        }

        public void setMethod(String method) {
            this.method = method.toUpperCase();
        }

        public ApiConnection buildApiConnection() {
            ApiConnectionType connectionType = ApiConnectionType.valueOf(type);

            switch (connectionType) {
                case WEBSOCKET:
                    return new WebsocketApiConnection(name, path, timeout);
                case REST:
                    return new RestApiConnection(name, path, Connection.Method.valueOf(method));
                case SOAP:
                    return new SoapApiConnection(name, path, Connection.Method.valueOf(method));
                default:
                    throw new IllegalArgumentException("Unknown api connection type: " + type);
            }
        }
    }
}
