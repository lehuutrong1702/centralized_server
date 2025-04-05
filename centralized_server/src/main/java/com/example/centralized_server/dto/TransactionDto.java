package com.example.centralized_server.dto;

import com.example.centralized_server.entity.TransactionStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class TransactionDto {
    private Long id;

    private Long fromUserId;

    private Long toUserId;

    private Long orderId;

    private String verifyAddress;

    private double price;

    private TransactionStatus status;

    private LocalDateTime createAt;
    private LocalDateTime updateAt;

}
