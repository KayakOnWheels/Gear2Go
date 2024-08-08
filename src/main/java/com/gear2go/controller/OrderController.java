package com.gear2go.controller;

import com.gear2go.dto.request.order.CreateOrderRequest;
import com.gear2go.dto.response.OrderResponse;
import com.gear2go.exception.ExceptionWithHttpStatusCode;
import com.gear2go.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/order")
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/all")
    public ResponseEntity<List<OrderResponse>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }


    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable Long id) throws ExceptionWithHttpStatusCode {
        return ResponseEntity.ok(orderService.getOrder(id));
    }


    @PostMapping
    public ResponseEntity<OrderResponse> createOrderFromCart(@RequestBody CreateOrderRequest createOrderRequest) throws ExceptionWithHttpStatusCode {
        return ResponseEntity.ok(orderService.createOrderFromCart(createOrderRequest));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) throws ExceptionWithHttpStatusCode {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }

}
