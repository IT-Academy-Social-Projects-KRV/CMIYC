package com.ms.authority.exception;

public class UserAlreadyRegistredException extends RuntimeException {

    public UserAlreadyRegistredException() {
        super("User already registered.");
    }

}