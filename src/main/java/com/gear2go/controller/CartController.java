package com.gear2go.controller;

import com.gear2go.dto.request.cart.CreateCartRequest;
import com.gear2go.dto.request.cart.UpdateCartRequest;
import com.gear2go.dto.response.CartResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/cart")
public class CartController {
    @GetMapping
    public ResponseEntity<List<CartResponse>> getAllCarts() {
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CartResponse> getCartById(@PathVariable Long id) {
        return null;
    }

    @PostMapping
    public ResponseEntity<CartResponse> createCart(@RequestBody CreateCartRequest createCartRequest) {
        return null;
    }

    @PutMapping
    public ResponseEntity<CartResponse> updateCart(@RequestBody UpdateCartRequest updateCartRequest) {
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCart(@PathVariable Long id) {
        return null;
    }

}
