package com.example.centralized_server.service;

import com.example.centralized_server.dto.OrderDto;

import java.util.List;

public interface OrderService {
    void createOrder(OrderDto orderDto);
    List<OrderDto> getAllOrdersByAddress(String address);
}
