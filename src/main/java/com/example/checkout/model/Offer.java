package com.example.checkout.model;

import java.util.Objects;
import java.util.UUID;

public class Offer {
    private final String id; // Immutable
    private String sku;
    private int requiredQuantity;
    private int offerPrice; // In cents

    public Offer(String sku, int requiredQuantity, int offerPrice) {
        this.id = UUID.randomUUID().toString();
        this.sku = sku;
        this.requiredQuantity = requiredQuantity;
        this.offerPrice = offerPrice;
    }

    public String getId() {
        return id;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public int getRequiredQuantity() {
        return requiredQuantity;
    }

    public void setRequiredQuantity(int requiredQuantity) {
        this.requiredQuantity = requiredQuantity;
    }

    public int getOfferPrice() {
        return offerPrice;
    }

    public void setOfferPrice(int offerPrice) {
        this.offerPrice = offerPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Offer offer = (Offer) o;
        return Objects.equals(id, offer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}