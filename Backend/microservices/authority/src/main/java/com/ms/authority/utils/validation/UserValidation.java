package com.ms.authority.utils.validation;

public class UserValidation {

    public static final int PASSWORD_MIN_LENGTH = 8;
    public static final int PASSWORD_MAX_LENGTH = 40;
    public static final String PASSWORD_PATTERN = "(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[$@$!%*?&])[A-Za-z0-9$@$!%*?&].*";

    private UserValidation() {
    }

    public static class Messages {

        public static final String TOKEN_NOT_BLANK_MSG = "Token is mandatory";
        public static final String PASSWORD_SIZE_MSG = "Password must be at least " + PASSWORD_MIN_LENGTH + " and not exceed " + PASSWORD_MAX_LENGTH + " characters.";
        public static final String PASSWORD_PATTERN_MSG = "Password should contain one number,one lowercase letters, one uppercase letters, and one special character.";

        private Messages() {
        }
    }
}