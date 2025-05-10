package com.example.centralized_server.service;


import com.example.centralized_server.dto.MetaDataDto;
import com.example.centralized_server.dto.OrderDto;
import com.example.centralized_server.dto.OrderRequest;
import com.example.centralized_server.entity.Order;
import com.example.centralized_server.entity.Status;
import org.springframework.data.domain.Page;

import com.google.type.DateTime;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface OrderService {
    void createOrder(OrderRequest orderRequest);
    List<OrderDto> getAllOrdersByAddress(String address);

    List<OrderDto>  getAllOrders(String search );
    List<OrderDto>  getOrdersByStatus(Status status, String name, LocalDateTime startDate, LocalDateTime endDate, String verifierAddress);
    OrderDto updateOrderStatus(Long id, Status status);

    OrderDto getByIdOrder(Long id);

    OrderDto updateTokenId(Long id, Long tokenId);

    OrderDto getByURI(String uri);

    List<OrderDto> getOrdersByUserId(Long userId);

    OrderDto updateMetaData(Long orderId, MetaDataDto metaDataDto);

    OrderDto update(OrderDto orderDto);

    Long count(String search);

    public long[] getMonthlyOrderCount(int year);

    Page<OrderDto> getOrders(Status status, String name, LocalDateTime startDate, LocalDateTime endDate, int page, int size, String address);
}
