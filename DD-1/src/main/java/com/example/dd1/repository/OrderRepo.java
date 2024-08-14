package com.example.dd1.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.dd1.entity.Order;

public interface OrderRepo extends JpaRepository<Order, Long> {
    List<Order> findByCustomerId(Long customerId);
}
