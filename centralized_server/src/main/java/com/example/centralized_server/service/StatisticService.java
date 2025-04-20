package com.example.centralized_server.service;

import com.example.centralized_server.dto.OrderDto;
import com.example.centralized_server.dto.StatisticDto;

import java.util.List;
import java.util.Map;


public interface StatisticService {
    StatisticDto getStatistics();

    List<Integer> countOrdersByMonth(String verifyAddress, int year);

    Map<String, Object> getLast7DaysOrderCounts(String verifyAddress);

    long countTransferredOrders();

    long countNonTransferredOrders();

    long countTransferredOrdersByVerifier(String verifyAddress);

    long countNonTransferredOrdersByVerifier(String verifyAddress);

    Map<String, Long> countUsersByRole();
    Map<String, Map<String, Long>> getUsersAndOrdersByWeek();

    long countOrderByVerifier(String verifyAddress);
    long countMemberByVerifier(String verifyAddress);
    long countCopyrightPublishedByVerifier(String verifyAddress);
    List<OrderDto> getFiveOrderNewest(String verifyAddress);
}
