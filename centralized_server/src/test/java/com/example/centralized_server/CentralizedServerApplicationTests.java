package com.example.centralized_server;

import com.example.centralized_server.dto.BrandCheckResult;
import com.example.centralized_server.service.BrandService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CentralizedServerApplicationTests {
	@Autowired
	private BrandService brandService;
//
//	@Test
//	void testExactMatch() {
//		BrandCheckResult result = brandService.checkBrand("CoolKids", "CoolKids");
//		System.out.println("Exact match: " + result);
//		assertTrue(result.isExactMatch());
//		assertTrue(result.isNormalizedMatch());
//		assertEquals(1.0, result.getSimilarityScore());
//		assertTrue(result.isPhoneticMatch());
//	}

	@Test
	void testNormalizedMatch() {
		BrandCheckResult result = brandService.checkBrand("Meet", "Meat");
		System.out.println("Exact match: " + result);
		assertFalse(result.isExactMatch());
		assertTrue(result.isNormalizedMatch());
		assertTrue(result.getSimilarityScore() > 0.8);
		assertTrue(result.isPhoneticMatch());
	}

//	@Test
//	void testLevenshteinSimilarity() {
//		BrandCheckResult result = brandService.checkBrand("KoolKidz", "KoolKid");
//		assertTrue(result.getSimilarityScore() > 0.8);
//	}
//
//	@Test
//	void testPhoneticMatch() {
//		BrandCheckResult result = brandService.checkBrand("KoolKidz", "CoolKids");
//		assertTrue(result.isPhoneticMatch());
//	}
//
//	@Test
//	void testNoMatch() {
//		BrandCheckResult result = brandService.checkBrand("TechGiant", "TechSoft");
//		assertFalse(result.isExactMatch());
//		assertFalse(result.isNormalizedMatch());
//		assertTrue(result.getSimilarityScore() < 0.5);
//		assertFalse(result.isPhoneticMatch());
//	}
}
