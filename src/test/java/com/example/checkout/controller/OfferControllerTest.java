package com.example.checkout.controller;

import com.example.checkout.model.Offer;
import com.example.checkout.repository.OfferRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OfferController.class)
class OfferControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private OfferRepository offerRepository;

    @Test
    void testCreateOffer() throws Exception {
        mockMvc.perform(post("/offers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": \"O01\", \"sku\": \"A01\", \"requiredQuantity\": 3, \"offerPrice\": 5}"))
                .andExpect(status().isOk());

        Mockito.verify(offerRepository).save(any(Offer.class));
    }


    @Test
    void testGetOfferNotFound() throws Exception {
        Mockito.when(offerRepository.findById("O01")).thenReturn(Optional.empty());

        mockMvc.perform(get("/offers/O01"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testUpdateOffer() throws Exception {
        Offer existingOffer = new Offer("A01", 3,  5);
        Mockito.when(offerRepository.findById("O01")).thenReturn(Optional.of(existingOffer));

        mockMvc.perform(put("/offers/O01")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": \"O01\", \"sku\": \"A01\", \"requiredQuantity\": 4, \"offerPrice\": 6}"))
                .andExpect(status().isOk());

        Mockito.verify(offerRepository).save(any(Offer.class));
    }

    @Test
    void testUpdateOfferNotFound() throws Exception {
        Mockito.when(offerRepository.findById("O01")).thenReturn(Optional.empty());

        mockMvc.perform(put("/offers/O01")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": \"O01\", \"sku\": \"A01\", \"requiredQuantity\": 4, \"offerPrice\": 6}"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteOffer() throws Exception {
        mockMvc.perform(delete("/offers/O01"))
                .andExpect(status().isOk());

        Mockito.verify(offerRepository).deleteById(eq("O01"));
    }
}