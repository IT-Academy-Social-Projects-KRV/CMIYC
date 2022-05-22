package com.ms.authority.exception;

public class BadRegisterDataException extends RuntimeException {

    public BadRegisterDataException() {
        super("Not enough data");
    }

}