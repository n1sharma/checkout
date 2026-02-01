package com.example.checkout.service;

import com.example.checkout.model.Cart;
import com.example.checkout.model.Item;
import com.example.checkout.model.Offer;
import com.example.checkout.repository.ItemRepository;
import com.example.checkout.repository.OfferRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CheckoutService {
    private final ItemRepository itemRepository;
    private final OfferRepository offerRepository;
    private final PriceCalculator priceCalculator;
    private final PricingRules pricingRules;

    public CheckoutService(ItemRepository itemRepository, OfferRepository offerRepository, PriceCalculator priceCalculator, PricingRules pricingRules) {
        this.itemRepository = itemRepository;
        this.offerRepository = offerRepository;
        this.priceCalculator = priceCalculator;
        this.pricingRules = pricingRules;
    }

    public int checkout(List<String> skus) {
        if(skus == null || skus.isEmpty()) {
            return 0;
        }
        Cart cart = new Cart();
        for (String sku : skus) {
            cart.addItem(sku, 1);
        }

        // Fetch all items and offers
        Map<String, Item> items = itemRepository.findAll();
        Map<String, Offer> offers = offerRepository.findAll();

        // Map offers by SKU for easier lookup
        Map<String, Offer> offersBySku = offers.values().stream()
                .collect(Collectors.toMap(Offer::getSku, offer -> offer));

        // Calculate total with offers
        int total = 0;
        for (Map.Entry<String, Integer> entry : cart.getItems().entrySet()) {
            String sku = entry.getKey();
            int quantity = entry.getValue();

            if (!items.containsKey(sku)) { // Skip non-existent SKUs
                continue;
            }

            if (offersBySku.containsKey(sku)) {
                Offer offer = offersBySku.get(sku);
                if (quantity >= offer.getRequiredQuantity()) {
                    // Apply offer price for eligible quantities
                    int offerBundles = quantity / offer.getRequiredQuantity();
                    int remainingItems = quantity % offer.getRequiredQuantity();
                    total += offerBundles * offer.getOfferPrice();
                    total += remainingItems * items.get(sku).getUnitPrice();
                } else {
                    // No offer applied, calculate regular price
                    total += quantity * items.get(sku).getUnitPrice();
                }
            } else {
                // No offer available, calculate regular price
                total += quantity * items.get(sku).getUnitPrice();
            }
        }

        return total;
    }
}