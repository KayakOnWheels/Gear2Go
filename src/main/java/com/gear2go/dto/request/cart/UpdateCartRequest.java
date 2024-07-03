package com.gear2go.dto.request.cart;

import com.gear2go.entity.CartItem;

import java.time.LocalDate;
import java.util.List;

public record UpdateCartRequest (Long id, LocalDate rentDate, LocalDate returnDate, List<CartItem> cartItemList) {
}
