package com.ms.data.exception;

public class InvalidSchemaException extends RuntimeException {

    public InvalidSchemaException() {
        super("The schema file is invalid, please select another file");
    }
}
