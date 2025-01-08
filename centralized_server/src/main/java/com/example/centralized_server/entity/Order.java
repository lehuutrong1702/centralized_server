package com.example.centralized_server.entity;

import jakarta.persistence.*;


@Table(name = "orders")
@Entity
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    @Column(nullable = false)
    private boolean status;

    @ManyToOne()
    @JoinColumn(name="user_id",nullable = false)
    private User user;


    @OneToOne(mappedBy = "order")
    private MetaData metaData;
}
