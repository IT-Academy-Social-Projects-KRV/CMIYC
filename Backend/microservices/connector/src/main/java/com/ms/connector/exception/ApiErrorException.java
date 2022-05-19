package com.ms.connector.exception;

public class ApiErrorException extends RuntimeException {

    public ApiErrorException(String errorMessage) {
        super(errorMessage);
    }
}
