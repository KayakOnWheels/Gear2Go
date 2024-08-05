package com.gear2go.dto.request.cart;

import java.time.LocalDate;

public record UpdateCartRentDatesRequest(LocalDate rentDate, LocalDate returnDate) {
}
