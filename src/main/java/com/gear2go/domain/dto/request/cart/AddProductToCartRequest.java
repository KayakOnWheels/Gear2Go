package com.gear2go.dto.request.cart;

import org.springframework.lang.Nullable;

public record AddProductToCartRequest(@Nullable String userMail, Long productId, Integer quantity) {
}
