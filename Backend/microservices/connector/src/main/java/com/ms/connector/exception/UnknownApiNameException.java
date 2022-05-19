package com.ms.connector.exception;

public class UnknownApiNameException extends RuntimeException {

    public UnknownApiNameException(String name) {
        super("Unknown api connection name: " + name);
    }
}
