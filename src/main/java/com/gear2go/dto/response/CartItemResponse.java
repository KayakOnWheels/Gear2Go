package com.gear2go.domain.dto.response;

import java.math.BigDecimal;

public record CartItemResponse(Long id, Long productId, String name, Integer quantity, BigDecimal price) {
}
