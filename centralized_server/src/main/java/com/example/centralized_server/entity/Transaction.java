package com.example.centralized_server.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    private Long id;

    private Long fromUserId;

    private Long toUserId;

    private Long orderId;

    private String verifyAddress;

    private double price;

    private TransactionStatus status;


    @Column(nullable = false, updatable = false)
    private LocalDateTime createAt;

    @Column(nullable = true)
    @LastModifiedDate
    private LocalDateTime updateAt;


    @PrePersist
    protected void onCreate() {
        this.createAt = LocalDateTime.now();
    }
}
