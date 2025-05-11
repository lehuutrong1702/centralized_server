package com.example.centralized_server.service.impl;

import com.example.centralized_server.dto.OrderDto;
import com.example.centralized_server.dto.SearchCriteria;
import com.example.centralized_server.dto.TransactionDto;
import com.example.centralized_server.dto.TransactionResponse;
import com.example.centralized_server.entity.Order;
import com.example.centralized_server.entity.Transaction;
import com.example.centralized_server.entity.TransactionStatus;
import com.example.centralized_server.entity.User;
import com.example.centralized_server.exception.ResourceNotFoundException;
import com.example.centralized_server.mapper.TransactionMapper;
import com.example.centralized_server.repository.OrderRepository;
import com.example.centralized_server.repository.TransactionRepository;
import com.example.centralized_server.repository.UserRepository;
import com.example.centralized_server.service.TransactionService;
import com.example.centralized_server.utils.CustomSpecification;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.time.LocalDateTime;
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
        System.out.println(transaction.getFromUserId());
        transaction.setFromUserId(transactionDto.getFromUserId());
        transaction.setToUserId(transactionDto.getToUserId());
        transaction.setOrderId(transactionDto.getOrderId());
        transaction.setVerifyAddress(transactionDto.getVerifyAddress());
        transaction.setPrice(transactionDto.getPrice());
        transaction.setStatus(transactionDto.getStatus());
        transaction.setCreateAt(LocalDateTime.now());
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
                        order.get().getVerifyAddress(),
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
                        order.get().getVerifyAddress(),
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
                        order.get().getVerifyAddress(),
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
                            order.get().getVerifyAddress(),
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
            String value = matcher.group(3);
            if(matcher.group(1).equals("createAt")) {

            }
            Specification<Transaction> spec = new CustomSpecification<>(
                    new SearchCriteria(matcher.group(1),
                            matcher.group(2),
                            matcher.group(3)));
            return transactionRepository.count(spec);

        } else {
            throw new RuntimeException("Search not found");
        }
    }
    @Override
    public long[] getMonthlyTransactionsCount(int year) {
        List<Object[]> result = transactionRepository.countTransactionsPerMonth((year));
        return getLongs(result);
    }
    private boolean matchOrderName(Long orderId, String search) {
        if (orderId == null || search == null || search.isBlank()) {
            return false;
        }
        return orderRepository.findById(orderId)
                .map(order -> {
                    if (order.getMetaData() != null && order.getMetaData().getName() != null) {
                        return order.getMetaData().getName().toLowerCase().contains(search.toLowerCase());
                    }
                    return false;
                })
                .orElse(false);
    }

    @Override
    public Page<TransactionResponse> getTransactions(TransactionStatus transactionStatus, String search, LocalDateTime startDate, LocalDateTime endDate, int page, int size, String address) {
        Page<Transaction> transactionsPage = transactionRepository.findAllByStatus(transactionStatus, address, PageRequest.of(page, size));
        System.out.println("ddd ss " + transactionsPage.getContent().size());
        List<Transaction> filteredTransactions = transactionsPage.getContent().stream()
                .filter(transaction -> {
                    boolean matchesSearch = (search == null || search.isBlank())
                            || (transaction.getFromUserId() != null && transaction.getFromUserId().toString().contains(search))
                            || (transaction.getToUserId() != null && transaction.getToUserId().toString().contains(search))
                            || (matchOrderName(transaction.getOrderId(), search));

                    boolean isAfterStart = (startDate == null)
                            || (transaction.getCreateAt() != null && !transaction.getCreateAt().isBefore(startDate));
                    boolean isBeforeEnd = (endDate == null)
                            || (transaction.getCreateAt() != null && !transaction.getCreateAt().isAfter(endDate));

                    boolean matchesAddress = (address == null || address.isBlank())
                            || (transaction.getVerifyAddress() != null && transaction.getVerifyAddress().toLowerCase().contains(address.toLowerCase()));
                    System.out.println("ddd" + matchesAddress);
                    return matchesSearch && isAfterStart && isBeforeEnd && matchesAddress;
                })
                .collect(Collectors.toList());
        List<TransactionResponse> responses = new ArrayList<>();

        for(Transaction transaction : filteredTransactions){

            Optional<User> fromUser = userRepository.findById(transaction.getFromUserId());
            Optional<User> toUser = userRepository.findById(transaction.getToUserId());
            Optional<Order> order = orderRepository.findById(transaction.getOrderId());
            if (fromUser.isPresent() && toUser.isPresent() && order.isPresent()) {
                TransactionResponse transactionResponse = new TransactionResponse(
                        transaction.getId(),
                        transaction.getOrderId(),
                        fromUser.get().getAddress(),
                        toUser.get().getAddress(),
                        order.get().getVerifyAddress(),
                        order.get().getMetaData().getName(),
                        transaction.getPrice(),
                        transaction.getStatus(),
                        transaction.getCreateAt(),
                        transaction.getUpdateAt()
                );
                responses.add(transactionResponse);
            }
        }



        return new PageImpl<>(responses, transactionsPage.getPageable(), transactionsPage.getTotalElements());
    }

    @Override
    public Long getSizeTransactions(TransactionStatus transactionStatus, String search, LocalDateTime startDate, LocalDateTime endDate, String address) {
        List<Transaction> transactions = transactionRepository.findAll();

        return transactions.stream()
                .filter(transaction -> {
                    boolean matchesStatus = (transactionStatus == null)
                            || (transaction.getStatus() != null && transaction.getStatus().equals(transactionStatus));

                    boolean matchesSearch = (search == null || search.isBlank())
                            || (transaction.getFromUserId() != null && transaction.getFromUserId().toString().contains(search))
                            || (transaction.getToUserId() != null && transaction.getToUserId().toString().contains(search))
                            || (matchOrderName(transaction.getOrderId(), search));

                    boolean isAfterStart = (startDate == null)
                            || (transaction.getCreateAt() != null && !transaction.getCreateAt().isBefore(startDate));
                    boolean isBeforeEnd = (endDate == null)
                            || (transaction.getCreateAt() != null && !transaction.getCreateAt().isAfter(endDate));

                    boolean matchesAddress = (address == null || address.isBlank())
                            || (transaction.getVerifyAddress() != null && transaction.getVerifyAddress().toLowerCase().contains(address.toLowerCase()));

                    return matchesStatus && matchesSearch && isAfterStart && isBeforeEnd && matchesAddress;
                })
                .count();
    }




    static long[] getLongs(List<Object[]> result) {
        long[] transactionPerMonth = new long[12];

        for (Object[] row : result) {
            int month = (int) row[0];
            long count = (long) row[1];
            transactionPerMonth[month - 1] = count;
        }

        return transactionPerMonth;
    }
}
