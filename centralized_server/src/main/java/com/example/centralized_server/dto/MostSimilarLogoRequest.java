package com.example.centralized_server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MostSimilarLogoRequest {
    private String baseImage;
    private String[] images;
}
