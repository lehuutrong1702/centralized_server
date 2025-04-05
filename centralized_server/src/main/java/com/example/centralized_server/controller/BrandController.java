package com.example.centralized_server.controller;


import com.example.centralized_server.dto.MostSimilarBrand;
import com.example.centralized_server.service.BrandService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/brand")
@AllArgsConstructor
public class BrandController {

    private BrandService brandService;

    @GetMapping("/mostSimilar")
    public ResponseEntity<MostSimilarBrand> getMostSimilarBrand(@RequestParam String brand) {
        return ResponseEntity.ok(brandService.checkMostSimilarBrand(brand));

    }
}
