package com.assignment.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users", indexes = {
        @Index(name = "FK_users_roles_idx", columnList = "role_id")
})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @Column(name = "username", nullable = false, length = 100)
    private String username;

    @Column(name = "fullname", nullable = false, length = 55)
    private String fullname;

    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "status", nullable = false)
    private Boolean status = false;

    @ManyToOne()
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;
}