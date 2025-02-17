package com.example.centralized_server.mapper;

import com.example.centralized_server.dto.OrderDto;
import com.example.centralized_server.entity.Order;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    Order toOrder(OrderDto orderDto);

    OrderDto toOrderDTO(Order order);
}
