package com.ms.connector.service.client;

import lombok.AccessLevel;
import lombok.Getter;

import java.util.Objects;

@Getter(AccessLevel.PROTECTED)
public abstract class AbstractClient {

    private final String baseUrl;
    private final String authorization;

    public AbstractClient(String baseUrl, String authorization) {
        Objects.requireNonNull(baseUrl);
        this.baseUrl = baseUrl;
        this.authorization = authorization;
    }
}
