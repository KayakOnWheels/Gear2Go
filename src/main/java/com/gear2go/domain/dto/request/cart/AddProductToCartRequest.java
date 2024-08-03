package com.gear2go.domain.dto.request.cart;

import org.springframework.lang.Nullable;

public record AddProductToCartRequest(@Nullable String userMail, Long productId, Integer quantity) {
}
