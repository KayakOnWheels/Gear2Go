package com.gear2go.dto.request.cart;

import java.time.LocalDate;

public record CreateCartRequest(Long userId, LocalDate rentDate, LocalDate returnDate) {
}
