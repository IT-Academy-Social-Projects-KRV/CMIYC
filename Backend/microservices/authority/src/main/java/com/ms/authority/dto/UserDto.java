package com.ms.authority.dto;

import java.time.LocalDate;
import java.util.Set;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * UserDto
 */
@Data
@Getter
@Setter
@NoArgsConstructor
public class UserDto {

    private Integer id;
    private String email;
    private String firstName;
    private String lastName;
    private boolean active;
    private LocalDate registerDate;
    private Set<String> scopes;
}