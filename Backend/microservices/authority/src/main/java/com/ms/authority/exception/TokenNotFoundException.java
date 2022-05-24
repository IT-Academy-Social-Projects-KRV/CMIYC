package com.ms.authority.exception;

public class TokenNotFoundException extends RuntimeException {

    public TokenNotFoundException() {
        super("Unable to activate, your activation link is invalid.");
    }

}