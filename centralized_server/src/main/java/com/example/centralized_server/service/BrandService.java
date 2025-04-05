package com.example.centralized_server.service;

import com.example.centralized_server.dto.BrandCheckResult;
import com.example.centralized_server.dto.MostSimilarBrand;

public interface BrandService {
    BrandCheckResult checkBrand(String brand1, String brand2);
    MostSimilarBrand checkMostSimilarBrand(String brand);

}
