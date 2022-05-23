package com.ms.authority.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import static com.ms.authority.utils.validation.UserValidation.Messages.PASSWORD_PATTERN_MSG;
import static com.ms.authority.utils.validation.UserValidation.Messages.PASSWORD_SIZE_MSG;
import static com.ms.authority.utils.validation.UserValidation.Messages.TOKEN_NOT_BLANK_MSG;
import static com.ms.authority.utils.validation.UserValidation.PASSWORD_MAX_LENGTH;
import static com.ms.authority.utils.validation.UserValidation.PASSWORD_MIN_LENGTH;
import static com.ms.authority.utils.validation.UserValidation.PASSWORD_PATTERN;

@Getter
@Setter
@NoArgsConstructor
public class ConfirmRegisterData {
    @NotBlank(message = TOKEN_NOT_BLANK_MSG)
    private String token;
    @Pattern(regexp = PASSWORD_PATTERN, message = PASSWORD_PATTERN_MSG)
    @Size(min = PASSWORD_MIN_LENGTH, max = PASSWORD_MAX_LENGTH, message = PASSWORD_SIZE_MSG)
    private String password;
    private String confirmPassword;
    private String twoFactorAuthenticationCode;
    private String captchaResponse;

    public boolean arePasswordsEquals() {
        return password.equals(confirmPassword);
    }
}