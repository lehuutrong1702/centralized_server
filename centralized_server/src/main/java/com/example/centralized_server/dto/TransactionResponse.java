package com.example.centralized_server.dto;

import com.example.centralized_server.entity.TransactionStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Data
@Setter
@Getter
@AllArgsConstructor
public class TransactionResponse {
    private Long id;

    private Long orderId;

    private String fromUserAddress;

    private String toUserAddress;

    private String title;

    private double price;

    private TransactionStatus status;

    private LocalDateTime createAt;

    private LocalDateTime updateAt;
}
