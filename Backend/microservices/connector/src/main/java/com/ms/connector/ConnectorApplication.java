package com.ms.connector;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ConnectorApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConnectorApplication.class, args);
    }
}
