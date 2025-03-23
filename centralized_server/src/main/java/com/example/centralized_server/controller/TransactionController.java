package com.example.centralized_server.controller;


import com.example.centralized_server.dto.OrderDto;
import com.example.centralized_server.dto.OrderRequest;
import com.example.centralized_server.dto.TransactionDto;
import com.example.centralized_server.dto.TransactionResponse;
import com.example.centralized_server.service.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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


    @GetMapping()
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



//    @GetMapping()
//    public ResponseEntity<List<TransactionResponse>> search(
//            @RequestParam(value = "search", required = false) String search) {
//        List<TransactionResponse> transactionResponses = transactionService.findAllTransactions(search);
//        return ResponseEntity.ok(transactionResponses);
//
//    }

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

}
