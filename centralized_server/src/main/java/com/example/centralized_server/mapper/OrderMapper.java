package com.example.centralized_server.mapper;

import com.example.centralized_server.dto.OrderDto;
import com.example.centralized_server.entity.Order;

import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    Order toOrder(OrderDto orderDto);

    OrderDto toOrderDTO(Order order);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateOrderFromOrderDto(@MappingTarget Order order, OrderDto orderDto);

    @AfterMapping
    default void removeTrailingZeros(@MappingTarget OrderDto dto) {
        if (dto.getGasFee() != null) {
            dto.setGasFee(dto.getGasFee().stripTrailingZeros());
        }
    }

//    Order toOrder(OrderDto orderDto, Order order);
//
//    OrderDto toOrderDTO(Order order, OrderDto orderDto);
}
