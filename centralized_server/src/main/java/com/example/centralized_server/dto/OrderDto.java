package com.example.centralized_server.dto;


import com.example.centralized_server.entity.Status;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderDto {

    private Long orderId;

    private Status status;

    private UserDto user;

    private MetaDataDto metaData;

}
