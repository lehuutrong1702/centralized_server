package com.example.centralized_server.dto;

import com.example.centralized_server.entity.TransactionStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TransactionRequest {
    private Long fromUserId;

    private Long toUserId;

    private Long orderId;

    private String verifyAddress;

    private double price;

    private TransactionStatus status;
}
