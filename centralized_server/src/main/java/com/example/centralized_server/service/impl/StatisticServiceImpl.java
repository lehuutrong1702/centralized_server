package com.example.centralized_server.service.impl;

import com.example.centralized_server.dto.OrderDto;
import com.example.centralized_server.dto.SearchCriteria;
import com.example.centralized_server.dto.StatisticDto;
import com.example.centralized_server.entity.*;
import com.example.centralized_server.mapper.OrderMapper;
import com.example.centralized_server.repository.OrderRepository;
import com.example.centralized_server.repository.TransactionRepository;
import com.example.centralized_server.repository.UserRepository;
import com.example.centralized_server.service.StatisticService;
import com.example.centralized_server.service.TransactionService;
import com.example.centralized_server.utils.CustomSpecification;
import lombok.AllArgsConstructor;
import org.hibernate.stat.Statistics;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class StatisticServiceImpl implements StatisticService {
    private UserRepository userRepository;
    private TransactionRepository transactionRepository;
    private OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    @Override
    public StatisticDto getStatistics() {
            Long userCount = userRepository.count();
            Long transactionCount = transactionRepository.count();
            Long orderCount = orderRepository.count();
            return new StatisticDto(userCount,
                    orderCount,
                    transactionCount) ;

    }

    @Override
    public List<Integer> countOrdersByMonth(String verifyAddress, int year) {
        List<Integer> orderCounts = new ArrayList<>(Collections.nCopies(12, 0));

        List<Object[]> results = orderRepository.countOrdersByMonth(verifyAddress, year);

        for (Object[] result : results) {
            int month = (int) result[0] - 1; // Convert to 0-based index
            int count = ((Number) result[1]).intValue();
            orderCounts.set(month, count);
        }

        return orderCounts;
    }

    @Override
    public Map<String, Object> getLast7DaysOrderCounts(String verifyAddress) {
        Map<String, Object> last7DaysData = new LinkedHashMap<>();
        LocalDate today = LocalDate.now();

        for (int i = 0; i < 7; i++) {
            LocalDate date = today.minusDays(i);
            Long count = orderRepository.countByVerifyAddressAndMetaData_CreateAtBetween(
                    verifyAddress,
                    date.atStartOfDay(),
                    date.plusDays(1).atStartOfDay()
            );
            last7DaysData.put(date.toString(), count);
        }

        return last7DaysData;
    }

    public long countTransferredOrders() {
        return orderRepository.countTransferredOrders();
    }

    public long countNonTransferredOrders() {
        return orderRepository.countNonTransferredOrders();
    }

    public long countTransferredOrdersByVerifier(String verifyAddress) {
        return orderRepository.countTransferredOrdersByVerifier(verifyAddress);
    }

    public long countNonTransferredOrdersByVerifier(String verifyAddress) {
        return orderRepository.countNonTransferredOrdersByVerifier(verifyAddress);
    }


    public Map<String, Long> countUsersByRole() {
        long verifierCount = userRepository.findByRole(Role.VERIFIER).size();
        long userCount = userRepository.findByRole(Role.USER).size();
        long adminCount = userRepository.findByRole(Role.ADMIN).size();

        Map<String, Long> roleCounts = new HashMap<>();
        roleCounts.put("USER", userCount);
        roleCounts.put("VERIFIER", verifierCount);
        roleCounts.put("ADMIN", adminCount);
        return roleCounts;
    }

    @Override
    public Map<String, Map<String, Long>> getUsersAndOrdersByWeek() {
        Map<String, Map<String, Long>> stats = new HashMap<>();
        Map<String, Long> userStats = new HashMap<>();
        Map<String, Long> orderStats = new HashMap<>();

        LocalDate today = LocalDate.now();
        LocalDate firstDayOfMonth = today.with(TemporalAdjusters.firstDayOfMonth());

        for (int i = 0; i < 4; i++) {
            LocalDate startOfWeek = firstDayOfMonth.plusWeeks(i);
            LocalDate endOfWeek = startOfWeek.plusDays(6);

            LocalDateTime startDateTime = startOfWeek.atStartOfDay();
            LocalDateTime endDateTime = endOfWeek.atTime(23, 59, 59);

            Long userCount = userRepository.countUsersByWeek(startDateTime, endDateTime);
            Long orderCount = orderRepository.countOrdersByWeek(startDateTime, endDateTime);

            userStats.put("Week " + (i + 1) + " Users", userCount);
            orderStats.put("Week " + (i + 1) + " Orders", orderCount);
        }

        stats.put("user", userStats);
        stats.put("order", orderStats);

        return stats;
    }

    @Override
    public long countOrderByVerifier(String verifyAddress) {
        return orderRepository.countOrdersByVerifier(verifyAddress);
    }

    @Override
    public long countMemberByVerifier(String verifyAddress) {
        Optional<User> verifier = userRepository.findByAddress(verifyAddress);
        if (verifier.isEmpty()) {
            return 0;
        }
        Long verifierId = verifier.get().getId();
        return userRepository.countByVerifierId(verifierId);
    }


    @Override
    public long countCopyrightPublishedByVerifier(String verifyAddress) {
        return orderRepository.countPublishedOrdersByVerifier(verifyAddress, Status.PUBLISHED);

    }


    @Override
    public List<OrderDto> getFiveOrderNewest(String verifyAddress) {
        Pageable topFive = PageRequest.of(0, 5);
        List<Order> orders = orderRepository.findTop5ByVerifyAddress(verifyAddress, topFive);
        List<OrderDto> result = new ArrayList<>();
        for(Order o : orders){
            OrderDto orderDto = orderMapper.toOrderDTO(o);
            result.add(orderDto);
        }
        return result;
    }
}
