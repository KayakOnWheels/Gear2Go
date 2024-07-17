package com.gear2go.controller;

import com.gear2go.dto.request.cart.AddProductToCartRequest;
import com.gear2go.dto.request.cart.CreateCartRequest;
import com.gear2go.dto.request.cart.UpdateCartRentDatesRequest;
import com.gear2go.dto.response.CartResponse;
import com.gear2go.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

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

    @PutMapping("/{id}")
    public ResponseEntity<CartResponse> updateCartRentDates(@PathVariable Long id, @RequestBody UpdateCartRentDatesRequest updateCartRentDatesRequest) {
        return ResponseEntity.ok(cartService.updateCartRentDates(id, updateCartRentDatesRequest));
    }

    @PutMapping
    public ResponseEntity<CartResponse> addProductToCart(@RequestBody AddProductToCartRequest addProductToCartRequest) {
        return ResponseEntity.ok(cartService.addProductToCart(addProductToCartRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCart(@PathVariable Long id) {
        cartService.deleteCart(id);
        return ResponseEntity.noContent().build();
    }

}
