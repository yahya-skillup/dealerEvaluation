package com.comparison.price.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.comparison.price.dto.ProductDTO;
import com.comparison.price.dto.ProductsDTO;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class ProductService {

    private static ProductsDTO productsDTO = new ProductsDTO();

    private final ObjectMapper objectMapper = new ObjectMapper();

    public ProductsDTO getAll() {
        if (productsDTO != null && productsDTO.getProducts() != null && !productsDTO.getProducts().isEmpty()) {
            return productsDTO;
        }
        productsDTO = getDataFromJson();
        return productsDTO;
    }

    public ProductDTO getByProductName(String productName) {
        ProductDTO productDTO;
        if (productsDTO != null && productsDTO.getProducts() != null && !productsDTO.getProducts().isEmpty()) {
            productDTO = productsDTO.getProducts()
                    .stream().filter(product -> product.getProduct().equals(productName))
                    .findFirst().orElseThrow(() -> new RuntimeException("Product not found"));

        } else {
            productsDTO = getDataFromJson();
            productDTO = productsDTO.getProducts().stream().filter(product -> product.getProduct().equals(productName)).findFirst().orElseThrow(() -> new RuntimeException("Product not found"));
        }

        return productDTO;

    }

    private ProductsDTO getDataFromJson() {
        try {
            return objectMapper.readValue(new File("src/main/resources/json/products.json"), ProductsDTO.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
