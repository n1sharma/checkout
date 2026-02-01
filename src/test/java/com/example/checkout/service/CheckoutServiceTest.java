package com.example.checkout.service;

import com.example.checkout.model.Item;
import com.example.checkout.model.Offer;
import com.example.checkout.repository.ItemRepository;
import com.example.checkout.repository.OfferRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class CheckoutServiceTest {

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private OfferRepository offerRepository;

    @InjectMocks
    private CheckoutService checkoutService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCheckoutWithOffer() {
        // Mock items
        Item item = new Item("B01", "banana", 3);
        when(itemRepository.findAll()).thenReturn(Map.of("B01", item));

        // Mock offers
        Offer offer = new Offer("B01", 2, 5);
        when(offerRepository.findAll()).thenReturn(Map.of(offer.getId(), offer));

        // Test checkout
        List<String> items = List.of("B01", "B01");
        int total = checkoutService.checkout(items);

        // Verify total
        assertEquals(5, total);
    }

    @Test
    void testCheckoutWithoutOffer() {
        // Mock items
        Item item = new Item("B01", "banana", 3);
        when(itemRepository.findAll()).thenReturn(Map.of("B01", item));

        // No offers
        when(offerRepository.findAll()).thenReturn(Map.of());

        // Test checkout
        List<String> items = List.of("B01", "B01");
        int total = checkoutService.checkout(items);

        // Verify total
        assertEquals(6, total);
    }
    @Test
    void testCheckoutWithNullItems() {
        // Test checkout with null items
        int total = checkoutService.checkout(null);

        // Verify total
        assertEquals(0, total);
    }

    @Test
    void testCheckoutWithEmptyItems() {
        // Test checkout with an empty list
        List<String> items = List.of();
        int total = checkoutService.checkout(items);

        // Verify total
        assertEquals(0, total);
    }

    @Test
    void testCheckoutWithNonExistentSku() {
        // Mock items
        Item item = new Item("B01", "banana", 3);
        when(itemRepository.findAll()).thenReturn(Map.of("B01", item));

        // Test checkout with a non-existent SKU
        List<String> items = List.of("B01", "B02");
        int total = checkoutService.checkout(items);

        // Verify total (only valid SKU is counted)
        assertEquals(3, total);
    }
}