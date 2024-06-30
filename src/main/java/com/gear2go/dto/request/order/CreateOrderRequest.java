package com.gear2go.dto.request.order;

import java.time.LocalDate;

public record CreateOrderRequest(LocalDate orderDate, Long userId, Long cartId) {
}
