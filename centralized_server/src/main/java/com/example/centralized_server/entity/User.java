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

    @Column(nullable = false,unique = true)
    private String address;

    @OneToMany(mappedBy = "user")
    private Set<Order> orders;
}
