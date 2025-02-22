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
    private Status status;

    @ManyToOne()
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String verifyAddress;

    @OneToOne(mappedBy = "order",cascade = CascadeType.ALL)
    private MetaData metaData;

    @Column(name = "token_id", nullable = true)
    private Long tokenId;
}
