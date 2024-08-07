package com.gear2go.dto.request.cartitem;

import java.math.BigDecimal;

public record UpdateCartItemRequest(Long id, Integer quantity, BigDecimal price, Float weight, Long productId, Long cartId) {
}
