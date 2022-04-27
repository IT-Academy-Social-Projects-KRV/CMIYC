package com.ms.connector.exception;

public class XMLTypeMapperNotFoundException extends RuntimeException {

    public XMLTypeMapperNotFoundException(String typeName) {
        super("Mapper for type " + typeName + " not found.");
    }
}
