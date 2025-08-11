package com.e_commerce.orderservice.messaging;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderEventsProducer {
    private final KafkaTemplate<String, String> kafka;

    public void publishOrderCreated(String orderId) {
        kafka.send("order.created", orderId);
    }
}