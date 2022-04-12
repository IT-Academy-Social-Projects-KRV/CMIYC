package com.ms.authority.dto;


import lombok.*;

import java.util.Set;

@Getter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
public class RegistrationRequest {
    private String firstName;
    private String lastName;
    private String email;
    private Set<String> roles;
}
