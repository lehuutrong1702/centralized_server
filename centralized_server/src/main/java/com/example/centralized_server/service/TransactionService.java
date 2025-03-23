package com.example.centralized_server.service;

import com.example.centralized_server.dto.TransactionDto;
import com.example.centralized_server.dto.TransactionResponse;
import com.example.centralized_server.entity.Transaction;

import java.math.BigDecimal;
import java.util.List;
import java.util.SortedMap;

public interface TransactionService {
    TransactionDto createTransaction(TransactionDto transactionDto);
    TransactionResponse findByTransactionId(Long transactionId);
    List<TransactionResponse> findAllTransactions(String search);

    TransactionDto updateTransaction(TransactionDto transactionDto);
    Boolean deleteTransaction(Long transactionId);
    List<TransactionResponse> findTransactionsByVerifier(String address);
    List<TransactionResponse> findTransactionsByAddressToUser(String address);
}
