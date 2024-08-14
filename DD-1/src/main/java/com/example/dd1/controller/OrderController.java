package com.example.dd1.controller;

import com.example.dd1.entity.Order;
import com.example.dd1.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "http://localhost:3000")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/buy-now/{customerId}")
    public ResponseEntity<Order> createOrderFromCart(@PathVariable Long customerId) {
        Order createdOrder = orderService.createOrderFromCart(customerId);
        return ResponseEntity.ok(createdOrder);
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<List<Order>> getOrdersByCustomerId(@PathVariable Long customerId) {
        List<Order> orders = orderService.getOrdersByCustomerId(customerId);
        return ResponseEntity.ok(orders);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long orderId) {
        orderService.deleteOrder(orderId);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/count")
    public ResponseEntity<Long> getOrderCount() {
        long count = orderService.getOrderCount();
        return ResponseEntity.ok(count);
    }

}
