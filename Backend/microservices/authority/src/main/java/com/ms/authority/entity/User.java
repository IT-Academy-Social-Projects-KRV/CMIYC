package com.ms.authority.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "users")
@NoArgsConstructor

public class User {
    @Id
    @Column(name = "id_user", nullable = false)
    private Integer id;
    @Column(name = "first_name", length = 50, nullable = false)
    private String firstName;
    @Column(name = "last_name", length = 50, nullable = false)
    private String lastName;
    @Column(name = "email", length = 50, nullable = false)
    private String email;
    @Column(name = "is_active", nullable = false)
    private boolean isActive;
    @Column(name = "register_date", nullable = false)
    private LocalDate registerDate;

}
