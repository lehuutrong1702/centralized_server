package com.example.centralized_server.service;

import com.example.centralized_server.dto.BrandCheckResult;
import com.example.centralized_server.dto.MostSimilarBrand;
import com.example.centralized_server.dto.MostSimilarLogo;

public interface BrandService {
    BrandCheckResult checkBrand(String brand1, String brand2);
    MostSimilarBrand checkMostSimilarBrand(long id);
    MostSimilarLogo checkMostSimilarLogo(String baseImageUrl, String[] imagesUrls);
}
