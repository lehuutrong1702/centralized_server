package com.example.centralized_server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MostSimilarBrand {
    private String brand;
    private double score;

}
