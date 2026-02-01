package com.example.checkout.service;

import com.example.checkout.model.Cart;
import com.example.checkout.model.Item;
import com.example.checkout.model.Offer;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class PriceCalculator {

    public int calculateTotal(Cart cart, Map<String, Item> items, PricingRules pricingRules) {
        int total = 0;

        for (Map.Entry<String, Integer> entry : cart.getItems().entrySet()) {
            String sku = entry.getKey();
            int quantity = entry.getValue();

            Item item = items.get(sku);
            if (item == null) continue;

            List<Offer> offers = pricingRules.getOffersForSku(sku);
            total += calculateCheapestPrice(item.getUnitPrice(), quantity, offers);
        }

        return total;
    }

    private int calculateCheapestPrice(int unitPrice, int quantity, List<Offer> offers) {
        int[] dp = new int[quantity + 1];
        for (int i = 1; i <= quantity; i++) {
            dp[i] = dp[i - 1] + unitPrice;
            for (Offer offer : offers) {
                if (i >= offer.getRequiredQuantity()) {
                    dp[i] = Math.min(dp[i], dp[i - offer.getRequiredQuantity()] + offer.getOfferPrice());
                }
            }
        }
        return dp[quantity];
    }
}