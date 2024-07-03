package com.gear2go.controller;

import com.gear2go.dto.request.cart.CreateCartRequest;
import com.gear2go.dto.request.cart.UpdateCartRequest;
import com.gear2go.dto.response.CartResponse;
import com.gear2go.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/cart")
public class CartController {

    CartService cartService;
    @GetMapping
    public ResponseEntity<List<CartResponse>> getAllCarts() {
        return ResponseEntity.ok(cartService.getAllCarts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CartResponse> getCartById(@PathVariable Long id) {
        return ResponseEntity.ok(cartService.getCart(id));
    }

    @PostMapping
    public ResponseEntity<CartResponse> createCart(@RequestBody CreateCartRequest createCartRequest) {
        return ResponseEntity.ok(cartService.createEmptyCart(createCartRequest));
    }

    @PutMapping
    public ResponseEntity<CartResponse> updateCart(@RequestBody UpdateCartRequest updateCartRequest) {
        return ResponseEntity.ok(cartService.updateCart(updateCartRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCart(@PathVariable Long id) {
        cartService.deleteCart(id);
        return ResponseEntity.noContent().build();
    }

}
