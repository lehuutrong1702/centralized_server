package com.example.centralized_server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MostSimilarLogo {
    private Long id;
    private String logo;
    private double rate;

}
