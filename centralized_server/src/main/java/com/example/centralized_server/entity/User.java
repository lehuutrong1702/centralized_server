package com.example.centralized_server.entity;


import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    @Column(nullable = false, name = "user_name")
    private String username;

    @Column(nullable = false)
    private String address;

    @OneToMany(mappedBy = "user")
    private Set<Order> orders;
}
