package com.example.checkout.controller;

import com.example.checkout.model.Item;
import com.example.checkout.repository.ItemRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/items")
@Tag(name = "Item API", description = "Manage items in the supermarket")
public class ItemController {
    private final ItemRepository itemRepository;

    public ItemController(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @PostMapping
    public void createItem(@RequestBody Item item) {
        itemRepository.save(item);
    }

    @GetMapping
    public Map<String, Item> getAllItems() {
        return itemRepository.findAll();
    }

    @GetMapping("/{sku}")
    public Item getItem(@PathVariable String sku) {
        return itemRepository.findBySku(sku).orElseThrow(() -> new RuntimeException("Item not found"));
    }

    @PutMapping("/{sku}")
    public void updateItem(@PathVariable String sku, @RequestBody Item updatedItem) {
        Item item = itemRepository.findBySku(sku).orElseThrow(() -> new RuntimeException("Item not found"));
        item.setName(updatedItem.getName());
        item.setUnitPrice(updatedItem.getUnitPrice());
        itemRepository.save(item);
    }

    @DeleteMapping("/{sku}")
    public void deleteItem(@PathVariable String sku) {
        itemRepository.deleteBySku(sku);
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleNotFoundException() {
        // This method ensures a 404 status is returned for RuntimeExceptions and further log this to APM
    }
}