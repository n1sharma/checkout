package com.example.checkout.service;

import com.example.checkout.model.Offer;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PricingRulesTest {

    @Test
    void testGetOffersForSkuWithExistingSku() {
        // Mock data
        Offer offer1 = new Offer("A01", 2, 15);
        Offer offer2 = new Offer("A01", 3, 20);
        PricingRules pricingRules = new PricingRules(List.of(offer1, offer2));

        // Test
        List<Offer> offers = pricingRules.getOffersForSku("A01");

        // Verify
        assertEquals(2, offers.size());
        assertEquals(offer1, offers.get(0));
        assertEquals(offer2, offers.get(1));
    }

    @Test
    void testGetOffersForSkuWithNonExistingSku() {
        // Mock data
        Offer offer1 = new Offer("A01", 2, 15);
        PricingRules pricingRules = new PricingRules(List.of(offer1));

        // Test
        List<Offer> offers = pricingRules.getOffersForSku("B01");

        // Verify
        assertEquals(0, offers.size());
    }

    @Test
    void testGetOffersForSkuWithEmptyOffers() {
        // Mock data
        PricingRules pricingRules = new PricingRules(List.of());

        // Test
        List<Offer> offers = pricingRules.getOffersForSku("A01");

        // Verify
        assertEquals(0, offers.size());
    }
}