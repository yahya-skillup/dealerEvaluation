package com.comparison.price.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.comparison.price.dto.DealersDTO;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.IOException;
import java.util.AbstractMap;
import java.util.List;

@Service
public class DealerService {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private static DealersDTO dealersDTO = new DealersDTO();

    public DealersDTO getAll() {
        if (dealersDTO != null && dealersDTO.getDealers() != null && !dealersDTO.getDealers().isEmpty()) {
            return dealersDTO;
        }
        dealersDTO = getDataFromJson();
        return dealersDTO;
    }


    public String getPriceByProductNameAndDealer(String productName, String dealer) {
        if (dealersDTO == null || CollectionUtils.isEmpty(dealersDTO.getDealers())) {
            dealersDTO = getDataFromJson();
        }

        return dealersDTO.getDealers().stream()
                .filter(dealerDTO -> dealerDTO.getDealer().equals(dealer))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Dealer not found"))
                .getProducts().getOrDefault(productName, "Product not found");
    }

    private DealersDTO getDataFromJson() {
        try {
            return objectMapper.readValue(new File("src/main/resources/json/dealers.json"), DealersDTO.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<AbstractMap.SimpleEntry<String, String>> getAllProductPrices(String productName) {
        if (dealersDTO == null || CollectionUtils.isEmpty(dealersDTO.getDealers())) {
            dealersDTO = getDataFromJson();
        }

        return dealersDTO.getDealers().stream()
                .filter(dealerDTO -> dealerDTO.getProducts().containsKey(productName))
                .map(dealerDTO -> new AbstractMap.SimpleEntry<>(dealerDTO.getDealer(), dealerDTO.getProducts().get(productName)))
                .toList();
    }
}
