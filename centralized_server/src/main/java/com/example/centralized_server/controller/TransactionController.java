package com.example.centralized_server.controller;


import com.example.centralized_server.dto.OrderDto;
import com.example.centralized_server.dto.OrderRequest;
import com.example.centralized_server.dto.TransactionDto;
import com.example.centralized_server.dto.TransactionResponse;
import com.example.centralized_server.entity.Status;
import com.example.centralized_server.entity.TransactionStatus;
import com.example.centralized_server.service.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/v1/transactions")
@AllArgsConstructor
public class TransactionController {
    private TransactionService transactionService;
    @PostMapping
    public ResponseEntity<TransactionDto> createOrder(@RequestBody TransactionDto transactionDto) {
        TransactionDto response = transactionService.createTransaction(transactionDto);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/searchByAddress")
    public ResponseEntity<List<TransactionResponse>> searchByAddress(
            @RequestParam(value = "address", required = false) String address, @RequestParam(value = "isVerify", defaultValue = "true") Boolean isVerify) {
        List<TransactionResponse> transactionResponses;
        if (isVerify) {
            transactionResponses = transactionService.findTransactionsByVerifier(address);
        } else {
            transactionResponses = transactionService.findTransactionsByAddressToUser(address);
        }
        return ResponseEntity.ok(transactionResponses);
    }



    @GetMapping()
    public ResponseEntity<List<TransactionResponse>> search(
            @RequestParam(value = "search", required = false) String search) {
        List<TransactionResponse> transactionResponses = transactionService.findAllTransactions(search);
        return ResponseEntity.ok(transactionResponses);

    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionResponse> searchById(
            @PathVariable String id) {
        TransactionResponse transactionResponse = transactionService.findByTransactionId(Long.valueOf(id));
        return ResponseEntity.ok(transactionResponse);
    }

    @PatchMapping()
    public ResponseEntity<TransactionDto> update(@RequestBody TransactionDto transactionDto) {
        TransactionDto transactionDto1 = null;
        transactionDto1 = transactionService.updateTransaction(transactionDto);
        return ResponseEntity.ok(transactionDto1);
    }

    @DeleteMapping()
    public ResponseEntity<Boolean> update(@RequestParam String id) {
        Boolean check = transactionService.deleteTransaction(Long.valueOf(id));
        return ResponseEntity.ok(check);
    }


    @GetMapping("/count")
    public ResponseEntity<long[]> count() {
        return ResponseEntity.ok(transactionService.getMonthlyTransactionsCount(LocalDateTime.now().getYear()));

    }


    @GetMapping("/getCopyrightTransfers")
    public ResponseEntity<Page<TransactionResponse>> getCopyrightTransfers(
            @RequestParam(required = false) TransactionStatus transactionStatus,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) LocalDateTime startDate,
            @RequestParam(required = false) LocalDateTime endDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String verifierAddress
    ) {
        System.out.println(verifierAddress);
        Page<TransactionResponse> orders = transactionService.getTransactions(transactionStatus, search, startDate, endDate, page - 1, size, verifierAddress);
        return ResponseEntity.ok(orders);

    }


    @GetMapping("/getSizeCopyrightTransfer")
    public ResponseEntity<Long> getAllCopyright(
            @RequestParam(required = false) TransactionStatus transactionStatus,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) LocalDateTime startDate,
            @RequestParam(required = false) LocalDateTime endDate,
            @RequestParam(required = false) String verifierAddress) {
        return ResponseEntity.ok(transactionService.getSizeTransactions(transactionStatus, search, startDate,endDate,verifierAddress));
    }
}
