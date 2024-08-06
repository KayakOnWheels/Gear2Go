package com.gear2go.dto.response;

import java.math.BigDecimal;

public record ProductResponse(Long id, String name, Float weight, BigDecimal price, Integer stock) {
}
