package com.example.centralized_server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BrandCheckResult {
    private boolean exactMatch;
    private boolean normalizedMatch;
    private double similarityScore;
    private boolean phoneticMatch;
}
