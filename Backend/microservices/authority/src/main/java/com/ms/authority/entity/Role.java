package com.ms.authority.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Table(name = "roles")
@NoArgsConstructor
public class Role {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column (name = "role", length = 25, nullable = false)
    private String role;
    @Column (name = "role_desc", length = 150, nullable = false)
    private String roleDesc;
}
