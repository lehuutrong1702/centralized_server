package com.example.centralized_server.service.impl;

import com.example.centralized_server.dto.SearchCriteria;
import com.example.centralized_server.dto.TransactionDto;
import com.example.centralized_server.dto.TransactionResponse;
import com.example.centralized_server.entity.Order;
import com.example.centralized_server.entity.Transaction;
import com.example.centralized_server.entity.User;
import com.example.centralized_server.exception.ResourceNotFoundException;
import com.example.centralized_server.mapper.TransactionMapper;
import com.example.centralized_server.repository.OrderRepository;
import com.example.centralized_server.repository.TransactionRepository;
import com.example.centralized_server.repository.UserRepository;
import com.example.centralized_server.service.TransactionService;
import com.example.centralized_server.utils.CustomSpecification;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private TransactionRepository transactionRepository;
    private UserRepository userRepository;
    private OrderRepository orderRepository;
    private TransactionMapper transactionMapper;

    @Override
    public TransactionDto createTransaction(TransactionDto transactionDto) {
        Transaction transaction = new Transaction();
        transaction.setFromUserId(transactionDto.getFromUserId());
        transaction.setToUserId(transactionDto.getToUserId());
        transaction.setOrderId(transactionDto.getOrderId());
        transaction.setVerifyAddress(transactionDto.getVerifyAddress());
        transaction.setPrice(transactionDto.getPrice());
        transaction.setStatus(transactionDto.getStatus());
        transactionRepository.save(transaction);

        //
        return transactionMapper.toTransactionDto(transaction);
    }

    @Override
    public TransactionResponse findByTransactionId(Long transactionId) {
        Optional<Transaction> transaction = transactionRepository.findById(transactionId);
        if (transaction.isPresent()) {
            Optional<User> fromUser = userRepository.findById(transaction.get().getFromUserId());
            Optional<User> toUser = userRepository.findById(transaction.get().getToUserId());
            Optional<Order> order = orderRepository.findById(transaction.get().getOrderId());
            if (fromUser.isPresent() && toUser.isPresent() && order.isPresent()) {
                TransactionResponse transactionResponse = new TransactionResponse(
                        transaction.get().getId(),
                        transaction.get().getOrderId(),
                        fromUser.get().getAddress(),
                        toUser.get().getAddress(),
                        order.get().getMetaData().getName(),
                        transaction.get().getPrice(),
                        transaction.get().getStatus(),
                        transaction.get().getCreateAt(),
                        transaction.get().getUpdateAt()
                );
                return transactionResponse;
            }
        }
        return null;
    }

    @Override
    public List<TransactionResponse> findAllTransactions(String search) {
        List<Transaction> transactions = new ArrayList<>();

        if (search == null) {
            transactions = transactionRepository.findAll();
        } else {
            Pattern pattern = Pattern.compile("(.*?)([<>:])(.*)");
            Matcher matcher = pattern.matcher(search);
            if (matcher.matches()) {
                Specification<Transaction> spec = new CustomSpecification<>(
                        new SearchCriteria(matcher.group(1),
                                matcher.group(2),
                                matcher.group(3)));
                transactions = transactionRepository.findAll(spec);

            } else {
                return Collections.emptyList();
            }
        }

        List<TransactionResponse> transactionResponses = new ArrayList<>();
        for (Transaction transaction : transactions) {
            Optional<User> fromUser = userRepository.findById(transaction.getFromUserId());
            Optional<User> toUser = userRepository.findById(transaction.getToUserId());
            Optional<Order> order = orderRepository.findById(transaction.getOrderId());

            if (fromUser.isPresent() && toUser.isPresent() && order.isPresent()) {
                TransactionResponse transactionResponse = new TransactionResponse(
                        transaction.getId(),
                        transaction.getOrderId(),
                        fromUser.get().getAddress(),
                        toUser.get().getAddress(),
                        order.get().getMetaData().getName(),
                        transaction.getPrice(),
                        transaction.getStatus(),
                        transaction.getCreateAt(),
                        transaction.getUpdateAt()
                );
                transactionResponses.add(transactionResponse);
            }
        }
        return transactionResponses;
    }


    @Override
    public TransactionDto updateTransaction(TransactionDto transactionDto) {
        Optional<Transaction> transaction = transactionRepository.findById(transactionDto.getId());
        if (transaction.isPresent()) {
            Transaction trans = transaction.get();
            if (transactionDto.getPrice() == 0.0) {
                transactionDto.setPrice(trans.getPrice());  // Đặt thành null để MapStruct bỏ qua
            }
            transactionMapper.updateTransactionFromTransactionDto(trans, transactionDto);
            transactionRepository.save(trans);
            return transactionMapper.toTransactionDto(trans);
        } else
            throw new ResourceNotFoundException("Transaction not found");

    }

    @Override
    public Boolean deleteTransaction(Long transactionId) {
        Optional<Transaction> transaction = transactionRepository.findById(transactionId);
        if (transaction.isPresent()) {
            transactionRepository.delete(transaction.get());
            return true;
        }
        return false;
    }

    @Override
    public List<TransactionResponse> findTransactionsByVerifier(String address) {
        List<Transaction> transactions = transactionRepository.findByVerifyAddress(address);
        List<TransactionResponse> transactionResponses = new ArrayList<>();
        for (Transaction transaction : transactions) {
            Optional<User> fromUser = userRepository.findById(transaction.getFromUserId());
            Optional<User> toUser = userRepository.findById(transaction.getToUserId());
            Optional<Order> order = orderRepository.findById(transaction.getOrderId());
            if (fromUser.isPresent() && toUser.isPresent() && order.isPresent()) {
                TransactionResponse transactionResponse = new TransactionResponse(
                        transaction.getId(),
                        transaction.getOrderId(),
                        fromUser.get().getAddress(),
                        toUser.get().getAddress(),
                        order.get().getMetaData().getName(),
                        transaction.getPrice(),
                        transaction.getStatus(),
                        transaction.getCreateAt(),
                        transaction.getUpdateAt()
                );
                transactionResponses.add(transactionResponse);
            }

        }
        return transactionResponses;
    }

    @Override
    public List<TransactionResponse> findTransactionsByAddressToUser(String address) {
        Optional<User> toUser = userRepository.findByAddress(address);
        if (toUser.isPresent()) {
            List<Transaction> transactions = transactionRepository.findByToUserId(toUser.get().getId());

            List<TransactionResponse> transactionResponses = new ArrayList<>();
            for (Transaction transaction : transactions) {
                Optional<User> fromUser = userRepository.findById(transaction.getFromUserId());
                Optional<Order> order = orderRepository.findById(transaction.getOrderId());
                if (fromUser.isPresent() && order.isPresent()) {
                    TransactionResponse transactionResponse = new TransactionResponse(
                            transaction.getId(),
                            transaction.getOrderId(),
                            fromUser.get().getAddress(),
                            toUser.get().getAddress(),
                            order.get().getMetaData().getName(),
                            transaction.getPrice(),
                            transaction.getStatus(),
                            transaction.getCreateAt(),
                            transaction.getUpdateAt()
                    );
                    transactionResponses.add(transactionResponse);
                }

            }
            return transactionResponses;
        }

        return null;

    }

    @Override
    public Long count(String search) {
        Pattern pattern = Pattern.compile("(.*?)([<>:])(.*)");
        Matcher matcher = pattern.matcher(search);
        if (matcher.matches()) {
            Specification<Transaction> spec = new CustomSpecification<>(
                    new SearchCriteria(matcher.group(1),
                            matcher.group(2),
                            matcher.group(3)));
            return transactionRepository.count(spec);

        } else {
            throw new RuntimeException("Search not found");
        }
    }
}
