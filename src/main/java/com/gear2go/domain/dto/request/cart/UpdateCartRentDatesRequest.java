package com.gear2go.domain.dto.request.cart;

import org.springframework.lang.Nullable;

import java.time.LocalDate;

public record UpdateCartRentDatesRequest(LocalDate rentDate, LocalDate returnDate, @Nullable String mail) {
}
