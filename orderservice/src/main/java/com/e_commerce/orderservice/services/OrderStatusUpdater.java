package com.e_commerce.orderservice.services;

import com.e_commerce.orderservice.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderStatusUpdater {

    private final OrderRepository orderRepository;

    @KafkaListener(topics = "payment.succeeded", groupId = "order-updater")
    public void updateOrderStatus(String orderId) {
        orderRepository.findById(Long.valueOf(orderId)).ifPresent(order -> {
            order.setStatus("PAID");
            orderRepository.save(order);
        });
    }
}