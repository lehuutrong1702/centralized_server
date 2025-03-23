package com.example.centralized_server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatisticDto {
    private Long sumOfUsers;
    private Long sumOfOrders;
    private Long sumOfTransactions;
}
