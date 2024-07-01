package com.gear2go.controller;

import com.gear2go.dto.request.order.CreateOrderRequest;
import com.gear2go.dto.request.order.UpdateOrderRequest;
import com.gear2go.dto.response.OrderResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/order")
public class OrderController {

    @GetMapping
    public ResponseEntity<List<OrderResponse>> getAllOrders() {
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable Long id) {
        return null;
    }

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@RequestBody CreateOrderRequest createProductRequest) {
        return null;
    }

    @PutMapping
    public ResponseEntity<OrderResponse> updateOrder(@RequestBody UpdateOrderRequest updateOrderRequest) {
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        return null;
    }

}
