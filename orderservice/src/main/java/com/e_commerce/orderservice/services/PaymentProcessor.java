package com.e_commerce.orderservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentProcessor {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @KafkaListener(topics = "order.created", groupId = "payment-simulation")
    public void processPayment(String orderId) {
        System.out.println("Processing payment for order: " + orderId);

        // Simulate payment success
        kafkaTemplate.send("payment.succeeded", orderId);
    }
}
