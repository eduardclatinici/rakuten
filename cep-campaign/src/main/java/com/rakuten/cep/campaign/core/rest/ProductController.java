package com.rakuten.cep.campaign.core.rest;

import com.rakuten.cep.campaign.core.dto.ProductRateProjection;
import com.rakuten.cep.campaign.core.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/campaign/products")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @PostMapping("/highest-rate")
    public Set<ProductRateProjection> getHighestRateProductsForIdsIn(HttpEntity<Set<Integer>> productIds) {
        return productRepository.getHighestRateProductsForIdsIn(productIds.getBody());
    }

}
