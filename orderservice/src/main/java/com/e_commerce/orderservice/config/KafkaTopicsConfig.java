package com.e_commerce.orderservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopicsConfig {
    @Bean
    public NewTopic orderCreated() {          // name, partitions, replication
        return new NewTopic("order.created", 1, (short) 1);
    }
    @Bean
    public NewTopic paymentSucceeded() {
        return new NewTopic("payment.succeeded", 1, (short) 1);
    }
}
