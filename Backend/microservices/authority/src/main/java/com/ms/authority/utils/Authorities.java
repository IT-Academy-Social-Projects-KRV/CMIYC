package com.ms.authority.utils;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class Authorities {

    public static final String PROJECT_NAME = "CMIYC";
    public static final String PASSWORD_GRANT_TYPE = "password";
    public static final GrantedAuthority PRE_AUTH = new SimpleGrantedAuthority("pre_auth");
    public static final String TFA_GRANT_TYPE = "tfa";

    private Authorities() {
    }

    public static class Messages {

        public static final String COULD_NOT_AUTHENTICATE_USER_MSG = "Could not authenticate user: ";
        public static final String MISSING_TFA_CODE_MSG = "Missing TFA code";
        public static final String INVALID_TFA_CODE_MSG = "Invalid TFA code";
        public static final String MISSING_TFA_TOKEN_MSG = "Missing TFA token";
        public static final String INVALID_ACCESS_TOKEN_MSG = "Invalid access token";
        public static final String ACCESS_TOKEN_EXPIRED_MSG = "Access token expired";
        public static final String INVALID_CLIENT_MSG = "Client not valid: ";
        public static final String MISSING_CLIENT_MSG = "Client is missing or does not correspond to the TFA token";

        private Messages() {
        }
    }
}
