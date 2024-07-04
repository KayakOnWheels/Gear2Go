package com.gear2go.service;

import com.gear2go.dto.request.order.UpdateOrderStatusRequest;
import com.gear2go.dto.response.OrderResponse;
import com.gear2go.entity.Cart;
import com.gear2go.entity.Order;
import com.gear2go.entity.enums.OrderStatus;
import com.gear2go.mapper.OrderMapper;
import com.gear2go.mapper.OrderStatusMapper;
import com.gear2go.repository.CartRepository;
import com.gear2go.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final OrderMapper orderMapper;
    private final OrderStatusMapper orderStatusMapper;

    public List<OrderResponse> getAllOrders() {
        return orderMapper.toOrderResponseList(orderRepository.findAll());
    }

    public OrderResponse getOrder(Long id) {
        return orderMapper.toOrderResponse(orderRepository.findById(id).orElseThrow());
    }

    public OrderResponse updateOrderStatus(UpdateOrderStatusRequest updateOrderStatusRequest) throws Exception {
        Order order = orderRepository.findById(updateOrderStatusRequest.id()).orElseThrow();

        order.setOrderStatus(orderStatusMapper.toOrderStatus(updateOrderStatusRequest));
        orderRepository.save(order);
        return orderMapper.toOrderResponse(order);
    }

    public void deleteOrder(Long id) {
        Order order = orderRepository.findById(id).orElseThrow();
        orderRepository.delete(order);
    }

    public OrderResponse createOrderFromCart(Long cartId) {
        Cart cart = cartRepository.findById(cartId).orElseThrow();

        Order order = new Order(
                LocalDate.now(),
                OrderStatus.UNPAID,
                cart.getUser(),
                cart
        );

        orderRepository.save(order);
        return orderMapper.toOrderResponse(order);
    }
}