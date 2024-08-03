package com.gear2go.domain.dto.request.cart;

import java.time.LocalDate;

public record CreateCartRequest(Long userId, LocalDate rentDate, LocalDate returnDate) {
}
