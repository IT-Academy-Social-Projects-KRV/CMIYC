package com.ms.authority.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ConfirmRegisterData {
    private final String TOKEN_NOT_BLANK_MSG = "Token is mandatory";
    private final int PASSWORD_MIN_LENGTH = 8;
    private final int PASSWORD_MAX_LENGTH = 40;
    private final String PASSWORD_PATTREN = "(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[$@$!%*?&])[A-Za-z0-9$@$!%*?&].*";
    private final String PASSWORD_SIZE_MSG = "Password must be at least " + PASSWORD_MIN_LENGTH + " and not exceed " + PASSWORD_MAX_LENGTH + " characters.";
    private final String PASSWORD_PATTREN_MSG = "Password should contain one number,one lowercase letters, one uppercase letters, and one special character.";

    @NotBlank(message = TOKEN_NOT_BLANK_MSG)
    private String token;
    @Pattern(regexp = PASSWORD_PATTREN, message = PASSWORD_PATTREN_MSG)
    @Size(min = PASSWORD_MIN_LENGTH, max = PASSWORD_MAX_LENGTH, message = PASSWORD_SIZE_MSG)
    private String password;
    private String confirmPassword;
    private String twoFactorAuthenticationCode;

    public boolean arePasswordsEquals() {
        return password.equals(confirmPassword);
    }
}