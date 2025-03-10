package com.example.centralized_server.service.impl;

import com.example.centralized_server.dto.SearchCriteria;
import com.example.centralized_server.dto.TransactionDto;
import com.example.centralized_server.entity.Order;
import com.example.centralized_server.entity.Transaction;
import com.example.centralized_server.exception.ResourceNotFoundException;
import com.example.centralized_server.mapper.TransactionMapper;
import com.example.centralized_server.repository.TransactionRepository;
import com.example.centralized_server.service.TransactionService;
import com.example.centralized_server.utils.CustomSpecification;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private TransactionRepository transactionRepository;
    private TransactionMapper transactionMapper;
    @Override
    public TransactionDto createTransaction(TransactionDto transactionDto) {
        // check whether from_user is existed
        // check the order in any transaction
        // check from_user own this order
         // main flow
        Transaction transaction = transactionMapper.toTransaction(transactionDto);
        transactionRepository.save(transaction);



        //
        return transactionMapper.toTransactionDto(transaction);
    }

    @Override
    public TransactionDto findByTransactionId(Long transactionId) {
        Optional<Transaction> transaction = transactionRepository.findById(transactionId);


        if(transaction.isPresent()){
            return transactionMapper.toTransactionDto(transaction.get());

        } else {
            throw new ResourceNotFoundException("Transaction not found");
        }

    }

    @Override
    public List<TransactionDto> findAllTransactions(String search) {
        if (search == null){
            return transactionRepository
                    .findAll()
                    .stream()
                    .map(transactionMapper::toTransactionDto).collect(Collectors.toList());
        }

        Pattern pattern = Pattern.compile("(.*?)([<>:])(.*)");
        Matcher matcher = pattern.matcher(search);
        if(matcher.matches()){
            Specification<Transaction> spec = new CustomSpecification<Transaction>(
                    new SearchCriteria(matcher.group(1),
                            matcher.group(2),
                            matcher.group(3)));
            return transactionRepository.findAll(spec).stream().
                    map(transactionMapper::toTransactionDto).collect(Collectors.toList());
        }


        return null;
    }

    @Override
    public TransactionDto updateTransaction(TransactionDto transactionDto) {
        Optional<Transaction> transaction = transactionRepository.findById(transactionDto.getId());
        if(transaction.isPresent()){
            Transaction trans = transaction.get();
            transactionMapper.updateTransactionFromTransactionDto(trans, transactionDto);
            transactionRepository.save(trans);
            return transactionMapper.toTransactionDto(trans);
        } else
            throw new ResourceNotFoundException("Transaction not found");

    }

    @Override
    public void deleteTransaction(Long transactionId) {

    }
}
