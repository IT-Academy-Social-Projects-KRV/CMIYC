package com.ms.authority.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RegistrationResult {
    private final boolean isError;
    private final String message;
}
