package com.ms.authority.exception;

public class ImpossibleOperationException extends RuntimeException{

    public ImpossibleOperationException() {
    }

    public ImpossibleOperationException(String message) {
        super(message);
    }
    
}
