package com.example.checkout.repository;

import com.example.checkout.model.Item;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class ItemRepository {
    private final Map<String, Item> items = new HashMap<>();

    public void save(Item item) {
        items.put(item.getSku(), item);
    }

    public Optional<Item> findBySku(String sku) {
        return Optional.ofNullable(items.get(sku));
    }

    public void deleteBySku(String sku) {
        items.remove(sku);
    }

    public Map<String, Item> findAll() {
        return items;
    }
}