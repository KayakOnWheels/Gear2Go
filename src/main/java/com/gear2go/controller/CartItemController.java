package com.gear2go.controller;

import com.gear2go.dto.request.cartitem.CreateCartItemRequest;
import com.gear2go.dto.request.cartitem.UpdateCartItemRequest;
import com.gear2go.dto.response.CartItemResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/CartItemitem")
public class CartItemController {

    @GetMapping
    public ResponseEntity<List<CartItemResponse>> getAllCartItems() {
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CartItemResponse> getCartItemById(@PathVariable Long id) {
        return null;
    }

    @PostMapping
    public ResponseEntity<CartItemResponse> createCartItem(@RequestBody CreateCartItemRequest createCartItemRequest) {
        return null;
    }

    @PutMapping
    public ResponseEntity<CartItemResponse> updateCartItem(@RequestBody UpdateCartItemRequest updateCartItemRequest) {
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCartItem(@PathVariable Long id) {
        return null;
    }

}
