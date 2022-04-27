package com.ms.authority.dto;

import com.ms.authority.entity.Role;
import com.ms.authority.entity.User;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Getter
@Setter
@NoArgsConstructor
public class UserData {
    private Integer id;
    private String email;
    private String firstName;
    private String lastName;
    private boolean active;
    private LocalDate registerDate;
    private Set<String> scopes;

    public static UserData convertToUserData(User user) {
        UserData userData = new UserData();

        userData.setId(user.getId());
        userData.setEmail(user.getEmail());
        userData.setFirstName(user.getFirstName());
        userData.setLastName(user.getLastName());
        userData.setActive(user.isEnabled());
        userData.setRegisterDate(user.getRegisterDate());
        userData.setScopes(user.getRoles()
                .stream()
                .map(Role::getAuthority)
                .collect(Collectors.toSet()));

        return userData;
    }
}