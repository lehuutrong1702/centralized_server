package com.example.centralized_server.service.impl;

import com.example.centralized_server.dto.SearchCriteria;
import com.example.centralized_server.dto.StatisticDto;
import com.example.centralized_server.entity.Transaction;
import com.example.centralized_server.repository.OrderRepository;
import com.example.centralized_server.repository.TransactionRepository;
import com.example.centralized_server.repository.UserRepository;
import com.example.centralized_server.service.StatisticService;
import com.example.centralized_server.service.TransactionService;
import com.example.centralized_server.utils.CustomSpecification;
import lombok.AllArgsConstructor;
import org.hibernate.stat.Statistics;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@AllArgsConstructor
public class StatisticServiceImpl implements StatisticService {
    private UserRepository userRepository;
    private TransactionRepository transactionRepository;
    private OrderRepository orderRepository;

    @Override
    public StatisticDto getStatistics() {
            Long userCount = userRepository.count();
            Long transactionCount = transactionRepository.count();
            Long orderCount = orderRepository.count();
            return new StatisticDto(userCount,
                    orderCount,
                    transactionCount) ;

    }
}
