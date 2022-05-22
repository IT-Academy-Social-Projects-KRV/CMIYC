package com.ms.authority.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Table(name = "roles")
@NoArgsConstructor
@EqualsAndHashCode(of = "role")
public class Role implements GrantedAuthority {
    public static final String ROLE_USER = "user";
    public static final String ROLE_ADMIN_SCHEMA = "admin_schema";
    public static final String ROLE_ADMIN_USER = "admin_user";
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column (name = "role", length = 25, nullable = false, unique = true)
    private String role;
    @Column (name = "role_desc", length = 150, nullable = false)
    private String roleDesc;

    @Override
    public String getAuthority() {
        return role;
    }
}
