package com.external.exception;

public class BadRequestException extends RuntimeException {

    public BadRequestException(String json) {
        super("Server was unable to build request from JSON: " + json);
    }

}