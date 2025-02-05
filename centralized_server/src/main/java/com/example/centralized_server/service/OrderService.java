package com.example.centralized_server.service;


import com.example.centralized_server.dto.OrderDto;
import com.example.centralized_server.dto.OrderRequest;
import com.example.centralized_server.entity.Order;
import com.example.centralized_server.entity.Status;

import java.util.List;

public interface OrderService {
    void createOrder(OrderRequest orderRequest);
    List<OrderDto> getAllOrdersByAddress(String address);

    List<OrderDto>  getAllOrders();
    List<OrderDto>  getOrdersByStatus(Status status);
    OrderDto updateOrderStatus(Long id, Status status);

    OrderDto getByIdOrder(Long id);
}
