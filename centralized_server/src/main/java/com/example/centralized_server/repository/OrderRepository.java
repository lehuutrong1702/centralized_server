package com.example.centralized_server.repository;

import com.example.centralized_server.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserAddress(String address);
}
