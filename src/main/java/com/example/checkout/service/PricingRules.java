package com.example.checkout.service;

import com.example.checkout.model.Offer;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PricingRules {
    private final Map<String, List<Offer>> offersBySku;

    public PricingRules(List<Offer> offers) {
        this.offersBySku = offers.stream().collect(Collectors.groupingBy(Offer::getSku));
    }

    public List<Offer> getOffersForSku(String sku) {
        return offersBySku.getOrDefault(sku, List.of());
    }
}