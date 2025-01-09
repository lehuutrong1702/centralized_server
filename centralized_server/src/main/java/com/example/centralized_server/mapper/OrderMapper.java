package com.example.centralized_server.mapper;

import com.example.centralized_server.dto.OrderDto;
import com.example.centralized_server.entity.Order;

public class OrderMapper {

    public static Order mapToOrder(Order order, OrderDto orderDto) {

        if(orderDto.getStatus() != null){
            order.setStatus(orderDto.getStatus());
        }

        return order;
    }

    public static OrderDto mapToOrderDto(Order order,OrderDto orderDto) {
        orderDto.setStatus(order.getStatus());
        orderDto.setOrderId(order.getId());

        return orderDto;
    }
}
