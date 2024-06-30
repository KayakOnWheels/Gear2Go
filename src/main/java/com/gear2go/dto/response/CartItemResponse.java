package com.gear2go.dto.response;

import java.math.BigDecimal;

public record CartItemResponse(Long id, Integer quantity, BigDecimal price, Float weight, Long productId, Long cartId) {
}
