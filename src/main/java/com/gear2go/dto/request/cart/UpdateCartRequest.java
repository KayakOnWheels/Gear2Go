package com.gear2go.dto.request.cart;

import java.time.LocalDate;

public record UpdateCartRequest (Long id, Long userId, LocalDate rentDate, LocalDate returnDate) {
}
