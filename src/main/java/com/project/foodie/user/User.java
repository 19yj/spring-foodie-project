package com.project.foodie.user;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
public class User {
    // use Lombok so no need to write setters and getters manually for each field
    @Setter
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private String userId;

    @Getter
    private String username;
    private String name;
    private String email;

    @Getter
    private String password;
    private String phoneNumber;
    private String role;

    // default constructor (required for JPA)
    public User() {}

}
