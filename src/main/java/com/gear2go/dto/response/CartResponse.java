package com.gear2go.dto.response;

import java.time.LocalDate;
import java.util.List;

public record CartResponse(Long id, Long userId, LocalDate rentDate, LocalDate returnDate,
                           List<CartItemResponse> cartItemResponseList, java.math.BigDecimal totalPrice) {
}
