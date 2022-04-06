package com.ms.authority.utils;


import com.ms.authority.entity.Role;
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
  //  private Set<Role> role;
}
