package com.example.centralized_server;

import com.example.centralized_server.dto.BrandCheckResult;
import com.example.centralized_server.dto.MostSimilarLogo;
import com.example.centralized_server.service.BrandService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CentralizedServerApplicationTests {
	@Autowired
	private BrandService brandService;
	@Test
	public void testCheckMostSimilarLogo() {
		// Arrange
		String baseImageUrl = "https://gateway.pinata.cloud/ipfs/QmdYKTm3oTTgMTR5FejBA8BxeiVsr9dQRhutfHgpNFLCHj/";
		String[] imageUrls = {
				"https://gateway.pinata.cloud/ipfs/QmRMV3i1UySTACn1yZngyy8zMLpQ8TGK8MyjCwvNx4frwo",  // giống 100%
				"https://gateway.pinata.cloud/ipfs/QmdYKTm3oTTgMTR5FejBA8BxeiVsr9dQRhutfHgpNFLCHj/",                      // khác
		};

		// Act
		MostSimilarLogo result = brandService.checkMostSimilarLogo(baseImageUrl, imageUrls);
		System.out.println("----- RESULT ------");
		System.out.println(result.toString());
		// Assert
		assertNotNull(result);
		assertEquals(imageUrls[1], result.getLogo());

		assertTrue(result.getRate() > 0.99);
	}
}
