package com.e_commerce.orderservice.repositories;

import com.e_commerce.orderservice.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
