package com.gear2go.mapper;

import com.gear2go.domain.dto.response.OrderResponse;
import com.gear2go.entity.Order;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderMapper {

    public OrderResponse toOrderResponse(Order order) {
        return new OrderResponse(
                order.getId(),
                order.getOrderDate(),
                order.getUser().getId(),
                order.getCart().getId()
        );
    }

    public List<OrderResponse> toOrderResponseList(List<Order> orderList) {
        return orderList.stream()
                .map(this::toOrderResponse)
                .toList();
    }
}
