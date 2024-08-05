package com.gear2go.dto.request.product;

import java.math.BigDecimal;

public record CreateProductRequest(String name, Float weight, BigDecimal price, Integer stock) {
}
