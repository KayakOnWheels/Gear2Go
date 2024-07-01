package com.gear2go.dto.request.order;

import java.time.LocalDate;

public record UpdateOrderRequest(Long id, LocalDate orderDate, Long userId, Long cartId) {
}
