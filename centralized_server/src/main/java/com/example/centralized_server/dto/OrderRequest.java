package com.example.centralized_server.dto;

import com.example.centralized_server.entity.Status;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class OrderRequest {

    private Long id;
    private Status status;

    private String userAddress;

    private MetaDataDto metaData;

    private String verifierAddress;

    private Boolean isTransfer;
}
