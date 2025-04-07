package com.example.centralized_server.controller;


import com.example.centralized_server.dto.MostSimilarBrand;
import com.example.centralized_server.service.BrandService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/brand")
@CrossOrigin(origins = "http://localhost:3000")
@AllArgsConstructor
public class BrandController {

    private BrandService brandService;

    @GetMapping("/mostSimilar")
    public ResponseEntity<MostSimilarBrand> getMostSimilarBrand(@RequestParam String id) {
        return ResponseEntity.ok(brandService.checkMostSimilarBrand(Long.parseLong(id)));
    }
}
