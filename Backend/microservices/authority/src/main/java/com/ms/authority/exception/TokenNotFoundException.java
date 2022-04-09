package com.ms.authority.exception;

public class TokenNotFoundException extends RuntimeException {

    public TokenNotFoundException() {
        super("Token not found.");
    }

}