package com.example.centralized_server.entity;

public enum TransactionStatus {
    REQUESTED,
    PENDING,
    PAYMENT_REQUIRED,
    PAYMENT_COMPLETED,
    COMPLETED,
    CANCELLED_REQUIRED,
    CANCELLED
}
