package com.example.centralized_server.service;

import com.example.centralized_server.dto.OrderDto;
import com.example.centralized_server.dto.TransactionDto;
import com.example.centralized_server.dto.TransactionResponse;
import com.example.centralized_server.entity.Status;
import com.example.centralized_server.entity.Transaction;
import com.example.centralized_server.entity.TransactionStatus;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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
    Long count(String search);
    public long[] getMonthlyTransactionsCount(int year);

    Page<TransactionResponse> getTransactions(TransactionStatus transactionStatus, String search, LocalDateTime startDate, LocalDateTime endDate, int page, int size, String address);
    Long getSizeTransactions(TransactionStatus transactionStatus, String search, LocalDateTime startDate, LocalDateTime endDate, String address);

}
