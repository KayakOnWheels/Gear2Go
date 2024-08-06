package com.gear2go.dto.response;

import java.time.LocalDate;

public record OrderResponse(Long id, LocalDate orderDate, Long userId, Long cartId) {
}
