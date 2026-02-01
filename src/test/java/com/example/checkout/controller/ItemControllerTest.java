package com.example.checkout.controller;

import com.example.checkout.model.Item;
import com.example.checkout.repository.ItemRepository;
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

@WebMvcTest(ItemController.class)
class ItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ItemRepository itemRepository;

    @Test
    void testCreateItem() throws Exception {
        // Perform POST request
        mockMvc.perform(post("/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"sku\": \"A01\", \"name\": \"apple\", \"unitPrice\": 2}"))
                .andExpect(status().isOk());

        // Verify repository interaction
        Mockito.verify(itemRepository).save(any(Item.class));
    }

    @Test
    void testGetAllItems() throws Exception {
        // Mock repository response
        Item item = new Item("A01", "apple", 2);
        Mockito.when(itemRepository.findAll()).thenReturn(Map.of("A01", item));

        // Perform GET request
        mockMvc.perform(get("/items"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"A01\": {\"sku\": \"A01\", \"name\": \"apple\", \"unitPrice\": 2}}"));
    }

    @Test
    void testGetItem() throws Exception {
        // Mock repository response
        Item item = new Item("A01", "apple", 2);
        Mockito.when(itemRepository.findBySku("A01")).thenReturn(Optional.of(item));

        // Perform GET request
        mockMvc.perform(get("/items/A01"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"sku\": \"A01\", \"name\": \"apple\", \"unitPrice\": 2}"));
    }

    @Test
    void testGetItemNotFound() throws Exception {
        // Mock repository response
        Mockito.when(itemRepository.findBySku("A01")).thenReturn(Optional.empty());

        // Perform GET request
        mockMvc.perform(get("/items/A01"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testUpdateItem() throws Exception {
        // Mock repository response
        Item existingItem = new Item("A01", "apple", 2);
        Mockito.when(itemRepository.findBySku("A01")).thenReturn(Optional.of(existingItem));

        // Perform PUT request
        mockMvc.perform(put("/items/A01")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"sku\": \"A01\", \"name\": \"updated apple\", \"unitPrice\": 3}"))
                .andExpect(status().isOk());

        // Verify repository interaction
        Mockito.verify(itemRepository).save(any(Item.class));
    }

    @Test
    void testUpdateItemNotFound() throws Exception {
        // Mock repository response
        Mockito.when(itemRepository.findBySku("A01")).thenReturn(Optional.empty());

        // Perform PUT request
        mockMvc.perform(put("/items/A01")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"sku\": \"A01\", \"name\": \"updated apple\", \"unitPrice\": 3}"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteItem() throws Exception {
        // Perform DELETE request
        mockMvc.perform(delete("/items/A01"))
                .andExpect(status().isOk());

        // Verify repository interaction
        Mockito.verify(itemRepository).deleteBySku(eq("A01"));
    }
}