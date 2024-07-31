package com.gear2go.dto.request.cart;

public record AddProductToCartRequest(String userMail, Long productId, Integer quantity){
}
