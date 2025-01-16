package com.example.centralized_server.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "user_name")
    private String username;

    @Column(nullable = false, unique = true)
    private String address;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private Role role;


    @Column(nullable = false)
    boolean isActive;

    @OneToMany(mappedBy = "user")
    private Set<Order> orders;


}
