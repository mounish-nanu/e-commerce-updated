package com.e_commerce.orderservice.messaging;

import com.e_commerce.orderservice.repositories.OrderRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class OrderStatusListener {

    private OrderRepository orderRepository;

    @KafkaListener(topics = "payment.succeeded", groupId = "order-updater")
    @Transactional
    public void onPaymentSucceeded(String orderId) {
        orderRepository.findById(Long.valueOf(orderId)).ifPresent(o -> {
            o.setStatus("PAID");
            o.setUpdatedAt(LocalDateTime.now());
            // no need to save explicitly in a @Transactional method with JPA
        });
    }
}