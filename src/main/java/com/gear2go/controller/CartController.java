package com.gear2go.controller;

import com.gear2go.dto.request.MailRequest;
import com.gear2go.dto.request.cart.AddProductToCartRequest;
import com.gear2go.dto.request.cart.UpdateCartRentDatesRequest;
import com.gear2go.dto.response.CartResponse;
import com.gear2go.exception.ExceptionWithHttpStatusCode;
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

    @GetMapping("/all")
    public ResponseEntity<List<CartResponse>> getAllCarts() {
        return ResponseEntity.ok(cartService.getAllCarts());
    }


    @GetMapping("/admin")
    public ResponseEntity<CartResponse> getCartByUserMail(@RequestBody MailRequest mailRequest) throws ExceptionWithHttpStatusCode {
        return ResponseEntity.ok(cartService.getUserCart(mailRequest));
    }

    @GetMapping
    public ResponseEntity<CartResponse> getCart() throws ExceptionWithHttpStatusCode {
        return ResponseEntity.ok(cartService.getUserCart(null));
    }


    @PutMapping("/dates")
    public ResponseEntity<CartResponse> updateCartRentDates(@RequestBody UpdateCartRentDatesRequest updateCartRentDatesRequest) throws ExceptionWithHttpStatusCode {
        return ResponseEntity.ok(cartService.updateCartRentDates(updateCartRentDatesRequest));
    }

    @PutMapping("/admin/dates")
    public ResponseEntity<CartResponse> updateCartRentDatesAdmin(@RequestBody UpdateCartRentDatesRequest updateCartRentDatesRequest) throws ExceptionWithHttpStatusCode {
        return ResponseEntity.ok(cartService.updateCartRentDates(updateCartRentDatesRequest));
    }


    @PutMapping
    public ResponseEntity<CartResponse> addOrSubtractProductToCart(@RequestBody AddProductToCartRequest addProductToCartRequest) throws ExceptionWithHttpStatusCode {
        return ResponseEntity.ok(cartService.addOrSubtractProductToCart(addProductToCartRequest));
    }

}
