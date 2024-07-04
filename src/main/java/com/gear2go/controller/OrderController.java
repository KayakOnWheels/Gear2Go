package com.gear2go.controller;

import com.gear2go.dto.request.order.UpdateOrderStatusRequest;
import com.gear2go.dto.response.OrderResponse;
import com.gear2go.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/order")
public class OrderController {

    OrderService orderService;
    @GetMapping
    public ResponseEntity<List<OrderResponse>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrder(id));
    }

    @PostMapping("/{id}")
    public ResponseEntity<OrderResponse> createOrderFromCart(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.createOrderFromCart(id));
    }


    @PutMapping
    public ResponseEntity<OrderResponse> updateOrderStatus(@RequestBody UpdateOrderStatusRequest updateOrderStatusRequest) throws Exception{
        return ResponseEntity.ok(orderService.updateOrderStatus(updateOrderStatusRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }

}
