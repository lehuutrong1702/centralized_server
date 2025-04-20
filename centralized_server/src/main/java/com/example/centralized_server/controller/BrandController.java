package com.example.centralized_server.controller;


import com.example.centralized_server.dto.MostSimilarBrand;
import com.example.centralized_server.dto.MostSimilarLogo;
import com.example.centralized_server.dto.MostSimilarLogoRequest;
import com.example.centralized_server.service.BrandService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.parser.Entity;

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

    @GetMapping("/mostSimilarLogo")
    public ResponseEntity<MostSimilarLogo> getMostSimilarLogo(
            @RequestParam String id) {
        return ResponseEntity.ok(
                brandService.checkMostSimilarLogoID(Long.parseLong(id)));
    }
}
