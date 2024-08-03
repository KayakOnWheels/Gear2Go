package com.gear2go.domain.dto.request.order;

public record CreateOrderRequest(Long billingAddressId, Long shippingAddressId) {
}
