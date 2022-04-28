package com.ms.authority.dto;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Set;

@Getter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
public class RegistrationRequestData {
    private String firstName;
    private String lastName;
    private String email;
    private Set<String> roles;
}
