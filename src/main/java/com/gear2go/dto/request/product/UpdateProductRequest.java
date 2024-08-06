package com.gear2go.dto.request.product;

import java.math.BigDecimal;

public record UpdateProductRequest(Long id, String name, Float weight, BigDecimal price, Integer stock) {
}
