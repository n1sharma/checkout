package com.example.checkout.controller;

import com.example.checkout.model.Offer;
import com.example.checkout.repository.OfferRepository;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/offers")
public class OfferController {
    private final OfferRepository offerRepository;

    public OfferController(OfferRepository offerRepository) {
        this.offerRepository = offerRepository;
    }

    @PostMapping
    public void createOffer(@RequestBody Offer offer) {
        offerRepository.save(offer);
    }

    @GetMapping
    public Map<String, Offer> getAllOffers() {
        return offerRepository.findAll();
    }

    @GetMapping("/{id}")
    public Offer getOffer(@PathVariable String id) {
        return offerRepository.findById(id).orElseThrow(() -> new RuntimeException("Offer not found"));
    }

    @PutMapping("/{id}")
    public void updateOffer(@PathVariable String id, @RequestBody Offer updatedOffer) {
        Offer offer = offerRepository.findById(id).orElseThrow(() -> new RuntimeException("Offer not found"));
        offer.setSku(updatedOffer.getSku());
        offer.setRequiredQuantity(updatedOffer.getRequiredQuantity());
        offer.setOfferPrice(updatedOffer.getOfferPrice());
        offerRepository.save(offer);
    }

    @DeleteMapping("/{id}")
    public void deleteOffer(@PathVariable String id) {
        offerRepository.deleteById(id);
    }
}