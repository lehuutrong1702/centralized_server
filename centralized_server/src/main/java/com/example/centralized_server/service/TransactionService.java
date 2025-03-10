package com.example.centralized_server.service;

import com.example.centralized_server.dto.TransactionDto;
import com.example.centralized_server.entity.Transaction;

import java.util.List;

public interface TransactionService {
    TransactionDto createTransaction(TransactionDto transactionDto);
    TransactionDto findByTransactionId(Long transactionId);
    List<TransactionDto> findAllTransactions(String search);

    TransactionDto updateTransaction(TransactionDto transactionDto);
    void deleteTransaction(Long transactionId);

}
