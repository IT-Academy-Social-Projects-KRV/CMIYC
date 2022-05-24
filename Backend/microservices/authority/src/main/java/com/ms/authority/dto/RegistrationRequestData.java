package com.ms.authority.dto;


import com.ms.authority.exception.BadRegisterDataException;
import com.ms.authority.exception.NotEnoughRolesSelectedException;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
public class RegistrationRequestData {
    private String firstName;
    private String lastName;
    private String email;
    private Set<String> roles = new HashSet<>();

    public void validate() {
        if(firstName == null ||
                lastName == null ||
                email == null) {
            throw new BadRegisterDataException();
        }

        firstName = firstName.trim();
        lastName = lastName.trim();
        email = email.trim();

        if(firstName.isEmpty() || lastName.isEmpty() || email.isEmpty()) {
            throw new BadRegisterDataException();
        }

        if(roles.isEmpty()) {
            throw new NotEnoughRolesSelectedException();
        }
    }
}
