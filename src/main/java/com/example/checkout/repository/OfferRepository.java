package com.example.checkout.repository;

import com.example.checkout.model.Offer;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class OfferRepository {
    private final Map<String, Offer> offers = new HashMap<>();

    public void save(Offer offer) {
        offers.put(offer.getId(), offer);
    }

    public Optional<Offer> findById(String id) {
        return Optional.ofNullable(offers.get(id));
    }

    public void deleteById(String id) {
        offers.remove(id);
    }

    public Map<String, Offer> findAll() {
        return offers;
    }
}