package com.ms.authority.dto;


import lombok.*;

@Getter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
public class RegistrationRequest {
    private String firstName;
    private String lastName;
    private String email;
  //  private Set<Role> role;
}
