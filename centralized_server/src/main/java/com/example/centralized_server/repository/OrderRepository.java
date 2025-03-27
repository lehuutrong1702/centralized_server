package com.example.centralized_server.repository;

import com.example.centralized_server.entity.Order;
import com.example.centralized_server.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {
    List<Order> findByUserAddress(String address);
    List<Order> findByStatus(Status status);
    List<Order> findByUserId(Long userId);
    List<Order> findByStatusAndUser_Address(Status status, String address);

    @Query("SELECT MONTH(o.metaData.createAt), COUNT(o) FROM Order o WHERE YEAR(o.metaData.createAt) = :year GROUP BY MONTH(o.metaData.createAt)")
    List<Object[]> countOrdersPerMonth(@Param("year") int year);
}
