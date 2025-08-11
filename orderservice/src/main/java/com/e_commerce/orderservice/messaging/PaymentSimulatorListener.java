package com.e_commerce.orderservice.messaging;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentSimulatorListener {
    private final KafkaTemplate<String, String> kafka;

    @KafkaListener(topics = "order.created", groupId = "payment-sim")
    public void onOrderCreated(String orderId) {
        log.info("Processing payment for orderId {}", orderId);
        // Simulate success
        kafka.send("payment.succeeded", orderId);
    }
}
