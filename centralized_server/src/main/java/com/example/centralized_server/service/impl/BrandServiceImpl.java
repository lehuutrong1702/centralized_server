package com.example.centralized_server.service.impl;

import com.example.centralized_server.dto.BrandCheckResult;
import com.example.centralized_server.dto.MostSimilarBrand;
import com.example.centralized_server.entity.Order;
import com.example.centralized_server.repository.OrderRepository;
import com.example.centralized_server.service.BrandService;
import lombok.AllArgsConstructor;
import org.apache.commons.codec.language.Soundex;
import org.apache.commons.text.similarity.LevenshteinDistance;
import org.springframework.stereotype.Service;
import org.apache.commons.text.*;
import org.apache.commons.codec.*;
import java.text.Normalizer;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BrandServiceImpl implements BrandService {

    private OrderRepository orderRepository;
    private String normalize(String input) {
        return Normalizer.normalize(input, Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", "")
                .toLowerCase()
                .replaceAll("[^a-z0-9]", "");
    }

    @Override
    public BrandCheckResult checkBrand(String brandA, String brandB) {
        String normA = normalize(brandA);
        String normB = normalize(brandB);

        boolean exactMatch = brandA.equals(brandB);
        boolean normalizedMatch = normA.equals(normB);

        int distance = LevenshteinDistance.getDefaultInstance().apply(normA, normB);
        double similarityScore = 1.0 - (double) distance / Math.max(normA.length(), normB.length());

        Soundex soundex = new Soundex();
        boolean phoneticMatch = soundex.encode(brandA).equals(soundex.encode(brandB));

        return new BrandCheckResult(exactMatch, normalizedMatch, similarityScore, phoneticMatch);
    }

    @Override
    public MostSimilarBrand checkMostSimilarBrand(long id) {
        try {
            Optional<Order> optionalOrder = orderRepository.findById(id);

            if (optionalOrder.isEmpty()) {
                return new MostSimilarBrand(); // Trả về rỗng nếu không tìm thấy
            }

            Order currentOrder = optionalOrder.get();
            String currentName = currentOrder.getMetaData().getName();

            List<Order> orders = orderRepository.findAll();
            MostSimilarBrand mostSimilarBrand = new MostSimilarBrand("", 0.0, 0);

            for (Order order : orders) {
                if (order.getId() == id) continue; // Bỏ qua bản thân

                String compareName = order.getMetaData().getName();
                double similarity = checkBrand(currentName, compareName).getSimilarityScore();

                if (similarity > mostSimilarBrand.getScore()) {
                    mostSimilarBrand.setBrand(compareName);
                    mostSimilarBrand.setScore(similarity);
                    mostSimilarBrand.setId(order.getId());
                }
            }

            return mostSimilarBrand;

        } catch (Exception e) {
            throw new RuntimeException("Error checking most similar brand: " + e.getMessage(), e);
        }
    }

}
