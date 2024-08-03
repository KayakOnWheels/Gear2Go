package com.gear2go.service;

import com.gear2go.domain.dto.request.order.CreateOrderRequest;
import com.gear2go.domain.dto.request.order.UpdateOrderStatusRequest;
import com.gear2go.domain.dto.response.OrderResponse;
import com.gear2go.entity.Cart;
import com.gear2go.entity.Order;
import com.gear2go.entity.User;
import com.gear2go.entity.enums.OrderStatus;
import com.gear2go.exception.AddressNotFoundException;
import com.gear2go.exception.ExceptionWithHttpStatusCode;
import com.gear2go.exception.UserNotFoundException;
import com.gear2go.mapper.OrderMapper;
import com.gear2go.mapper.OrderStatusMapper;
import com.gear2go.repository.AddressRepository;
import com.gear2go.repository.CartRepository;
import com.gear2go.repository.OrderRepository;
import com.gear2go.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final OrderStatusMapper orderStatusMapper;
    private final UserRepository userRepository;
    private final AuthenticationService authenticationService;
    private final AddressRepository addressRepository;

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

    public OrderResponse createOrderFromCart(CreateOrderRequest createOrderRequest) throws ExceptionWithHttpStatusCode {

        Authentication authentication = authenticationService.getAuthentication();

        User user = userRepository.findUserByMail(authentication.getName()).orElseThrow(UserNotFoundException::new);
        Cart cart = user.getCart();

        Order order = new Order(
                LocalDate.now(),
                OrderStatus.UNPAID,
                user,
                cart,
                addressRepository.findById(createOrderRequest.billingAddressId()).orElseThrow(() -> new AddressNotFoundException(createOrderRequest.billingAddressId())),
                addressRepository.findById(createOrderRequest.shippingAddressId()).orElseThrow(() -> new AddressNotFoundException(createOrderRequest.shippingAddressId()))
        );

        orderRepository.save(order);
        return orderMapper.toOrderResponse(order);
    }
}