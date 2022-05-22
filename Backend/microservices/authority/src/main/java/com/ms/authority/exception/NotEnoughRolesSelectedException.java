package com.ms.authority.exception;

public class NotEnoughRolesSelectedException extends RuntimeException {

    public NotEnoughRolesSelectedException() {
        super("At least one role must be selected");
    }

}