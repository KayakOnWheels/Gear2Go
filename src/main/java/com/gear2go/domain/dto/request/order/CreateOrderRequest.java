package com.gear2go.dto.request.order;

public record CreateOrderRequest(Long billingAddressId, Long shippingAddressId) {
}
