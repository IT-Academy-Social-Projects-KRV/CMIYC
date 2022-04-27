package com.external.exception;

public class UnknownApiException extends RuntimeException {

    public UnknownApiException(String apiName) {
        super("Unknown api: " + apiName);
    }
}
