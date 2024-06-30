package com.gear2go.mapper;

import com.gear2go.dto.response.OrderResponse;
import com.gear2go.entity.Order;

import java.util.List;

public class OrderMapper {

    public OrderResponse toOrderResponse(Order order) {
        return new OrderResponse(
                order.getId(),
                order.getOrderDate(),
                order.getUser().getId(),
                order.getCart().getId()
        );
    }

    public List<OrderResponse> toOrderList(List<Order> orderList) {
        return orderList.stream()
                .map(this::toOrderResponse)
                .toList();
    }
}
