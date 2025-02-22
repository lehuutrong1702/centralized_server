package com.example.centralized_server.mapper;

import com.example.centralized_server.dto.OrderDto;
import com.example.centralized_server.entity.Order;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    Order toOrder(OrderDto orderDto);

    OrderDto toOrderDTO(Order order);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateOrderFromOrderDto(@MappingTarget Order order, OrderDto orderDto);

//    Order toOrder(OrderDto orderDto, Order order);
//
//    OrderDto toOrderDTO(Order order, OrderDto orderDto);
}
