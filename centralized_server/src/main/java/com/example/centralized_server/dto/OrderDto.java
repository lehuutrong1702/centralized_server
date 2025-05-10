package com.example.centralized_server.dto;


import com.example.centralized_server.entity.Status;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class OrderDto {
    private Long id;
    private Status status;

    private UserDto user;

    private MetaDataDto metaData;

    private Long tokenId;

    private Boolean isTransfer;

    private String verifyAddress;

    private BigDecimal gasFee;
}
