package com.example.centralized_server.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Table(name = "orders")
@Entity
@Getter
@Setter
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    @Column(nullable = false)
    private Boolean status;

    @ManyToOne()
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


    @OneToOne(mappedBy = "order")
    private MetaData metaData;
}
