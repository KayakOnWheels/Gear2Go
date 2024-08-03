package com.gear2go.domain.dto.request.product;

import java.time.LocalDate;

public record ProductAvailabilityInDateRangeRequest(Long productId, LocalDate rentDate, LocalDate returnDate) {
}
