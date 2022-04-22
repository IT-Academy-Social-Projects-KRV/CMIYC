package com.ms.connector.exception;

public class UnknownApiConnectionTypeException extends RuntimeException {

    public UnknownApiConnectionTypeException(String type) {
        super("Unknown api connection type: " + type);
    }
}
