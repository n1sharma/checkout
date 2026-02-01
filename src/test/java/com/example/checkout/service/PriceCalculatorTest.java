package com.example.checkout.service;

import com.example.checkout.model.Cart;
import com.example.checkout.model.Item;
import com.example.checkout.model.Offer;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PriceCalculatorTest {

    private final PriceCalculator priceCalculator = new PriceCalculator();

    @Test
    void testCalculateTotalWithValidOffers() {
        // Mock data
        Cart cart = new Cart();
        cart.addItem("A01", 3);

        Item item = new Item("A01", "Apple", 10);
        Offer offer = new Offer("A01", 2, 15);

        PricingRules pricingRules = new PricingRules(List.of(offer));

        // Calculate total
        int total = priceCalculator.calculateTotal(cart, Map.of("A01", item), pricingRules);

        // Verify total
        assertEquals(25, total); // 2 items for 15 (offer) + 1 item for 10 = 25
    }

    @Test
    void testCalculateTotalWithoutOffers() {
        // Mock data
        Cart cart = new Cart();
        cart.addItem("B01", 3);

        Item item = new Item("B01", "Banana", 5);

        PricingRules pricingRules = new PricingRules(List.of()); // No offers

        // Calculate total
        int total = priceCalculator.calculateTotal(cart, Map.of("B01", item), pricingRules);

        // Verify total
        assertEquals(15, total); // 3 items * 5 = 15
    }

    @Test
    void testCalculateTotalWithEmptyCart() {
        // Mock data
        Cart cart = new Cart();

        PricingRules pricingRules = new PricingRules(List.of());

        // Calculate total
        int total = priceCalculator.calculateTotal(cart, Map.of(), pricingRules);

        // Verify total
        assertEquals(0, total); // Empty cart = 0 total
    }

    @Test
    void testCalculateTotalWithNullItem() {
        // Mock data
        Cart cart = new Cart();
        cart.addItem("C01", 2);

        PricingRules pricingRules = new PricingRules(List.of());

        // Calculate total
        int total = priceCalculator.calculateTotal(cart, Map.of(), pricingRules);

        // Verify total
        assertEquals(0, total); // No item found for SKU = 0 total
    }

    @Test
    void testCalculateTotalWithMultipleOffers() {
        // Mock data
        Cart cart = new Cart();
        cart.addItem("D01", 5);

        Item item = new Item("D01", "Detergent", 20);
        Offer offer1 = new Offer("D01", 2, 35); // 2 for 35
        Offer offer2 = new Offer("D01", 3, 50); // 3 for 50

        PricingRules pricingRules = new PricingRules(List.of(offer1, offer2));

        // Calculate total
        int total = priceCalculator.calculateTotal(cart, Map.of("D01", item), pricingRules);

        // Verify total
        assertEquals(85, total); // 3 for 50 (offer2) + 2 for 35 (offer1) = 85
    }
}