package com.example.centralized_server.repository;

import com.example.centralized_server.entity.Order;
import com.example.centralized_server.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserAddress(String address);
    List<Order> findByStatus(Status status);
    List<Order> findByUserId(Long userId);
    List<Order> findByStatusAndUser_Address(Status status, String address);
}
