package com.e_commerce.orderservice.controllers;

import com.e_commerce.orderservice.messaging.OrderEventsProducer;
import com.e_commerce.orderservice.models.Order;
import com.e_commerce.orderservice.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/{userId}/create")
    public ResponseEntity<String> create(@PathVariable Long userId) {
        Order order = new Order();
        order.setUserId(userId);

        Order saved = orderService.createOrder(order);
        return ResponseEntity.ok("order.created sent for orderId=" + saved.getId());
    }
}
