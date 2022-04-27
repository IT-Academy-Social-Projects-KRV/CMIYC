package com.ms.connector.config;

import com.ms.connector.exception.UnknownApiConnectionTypeException;
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
        private int timeout = 10000;

        public ApiConnection buildApiConnection() {
            ApiConnection.Type connectionType = ApiConnection.Type.valueOf(type.toUpperCase());

            switch (connectionType) {
                case WEBSOCKET:
                    return new WebsocketApiConnection(name, path, timeout);
                case REST:
                    return new RestApiConnection(name, path, Connection.Method.valueOf(method.toUpperCase()));
                case SOAP:
                    return new SoapApiConnection(name, path, Connection.Method.valueOf(method.toUpperCase()));
                default:
                    throw new UnknownApiConnectionTypeException(type);
            }
        }
    }
}
