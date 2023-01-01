package com.openclassrooms.poseidon.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "username")
    @NotNull(message = "Username is mandatory")
    private String username;

    @NotNull(message = "Password is mandatory")
    @Column(name = "password")
    private String password;

    @NotNull(message = "FullName is mandatory")
    @Column(name = "fullname")
    private String fullname;

    @NotNull(message = "Role is mandatory")
    @Column(name = "role")
    private String role;

}
