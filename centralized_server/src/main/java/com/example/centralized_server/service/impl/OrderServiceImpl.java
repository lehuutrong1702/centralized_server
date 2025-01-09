package com.example.centralized_server.service.impl;

import com.example.centralized_server.dto.OrderDto;
import com.example.centralized_server.repository.OrderRepository;
import com.example.centralized_server.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    @Override
    public void createOrder(OrderDto orderDto) {

    }

    @Override
    public List<OrderDto> getAllOrdersByAddress(String address) {
        return List.of();
    }
}
