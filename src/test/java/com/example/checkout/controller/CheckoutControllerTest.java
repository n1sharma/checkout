package com.example.checkout.controller;

import com.example.checkout.service.CheckoutService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyList;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CheckoutController.class)
class CheckoutControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CheckoutService checkoutService;

    @Test
    void testCheckout() throws Exception {
        // Mock the service response
        Mockito.when(checkoutService.checkout(anyList())).thenReturn(100);

        // Perform the POST request
        mockMvc.perform(post("/checkout")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"items\": [\"item1\", \"item2\"]}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"total\": 100}"));

        // Verify the service was called
        Mockito.verify(checkoutService).checkout(List.of("item1", "item2"));
    }
}