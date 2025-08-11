package com.e_commerce.orderservice.services;

import com.e_commerce.orderservice.models.Order;
import com.e_commerce.orderservice.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class OrderService {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final OrderRepository orderRepository;

    public OrderService(KafkaTemplate<String, String> kafkaTemplate, OrderRepository orderRepository) {
        this.kafkaTemplate = kafkaTemplate;
        this.orderRepository = orderRepository;
    }

    public Order createOrder(Order order) {
        order.setStatus("PENDING");
        order.setCreatedAt(LocalDateTime.now());

        Order saved = orderRepository.saveAndFlush(order); // forces INSERT
        kafkaTemplate.send("order.created", saved.getId().toString()); // send ORDER ID
        return saved;
    }
}
