package com.gear2go.dto.request.product;

import java.math.BigDecimal;

public record UpdateProductRequest(Long id, String name, float weight, BigDecimal price, Integer stock) {
}
