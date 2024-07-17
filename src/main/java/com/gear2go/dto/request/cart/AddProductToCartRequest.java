package com.gear2go.dto.request.cart;

public record AddProductToCartRequest(Long cartId, Long productId, Integer quantity){
}
