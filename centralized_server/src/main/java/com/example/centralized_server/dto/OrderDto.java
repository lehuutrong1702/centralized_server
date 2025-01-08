package com.example.centralized_server.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderDto {

    private Long userId;

    private MetaDataDto metaData;

}
