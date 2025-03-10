package com.example.centralized_server.mapper;

import com.example.centralized_server.dto.OrderDto;
import com.example.centralized_server.dto.TransactionDto;
import com.example.centralized_server.entity.Order;
import com.example.centralized_server.entity.Transaction;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import javax.swing.*;

@Mapper(componentModel = "spring")
public interface TransactionMapper {
    Transaction toTransaction(TransactionDto transactionDto);

    TransactionDto toTransactionDto(Transaction transaction);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateTransactionFromTransactionDto(@MappingTarget Transaction transaction,
                                             TransactionDto transactionDto );
}
