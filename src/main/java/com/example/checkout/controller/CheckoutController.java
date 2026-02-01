package com.example.checkout.controller;

import com.example.checkout.service.CheckoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/checkout")
public class CheckoutController {

    private final CheckoutService checkoutService;

    public CheckoutController(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }

    @PostMapping
    public Map<String, Integer> checkout(@RequestBody Map<String, List<String>> request) {
        List<String> items = request.get("items");
        int total = checkoutService.checkout(items);
        return Map.of("total", total);
    }
}