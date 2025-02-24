package com.example.centralized_server.dto;


import lombok.*;

@Builder
@Data
@AllArgsConstructor
public class SearchCriteria {
    private String key;
    private String operation;
    private Object value;
}
