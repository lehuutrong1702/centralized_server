package com.example.centralized_server.repository;

import com.example.centralized_server.entity.Order;
import com.example.centralized_server.entity.Status;
import org.springframework.data.domain.Page;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {
    List<Order> findByUserAddress(String address);
    List<Order> findByStatus(Status status);
    List<Order> findByUserId(Long userId);
    List<Order> findByStatusAndUser_Address(Status status, String address);

    @Query("SELECT MONTH(o.metaData.createAt), COUNT(o) FROM Order o WHERE YEAR(o.metaData.createAt) = :year GROUP BY MONTH(o.metaData.createAt)")
    List<Object[]> countOrdersPerMonth(@Param("year") int year);

    @Query("SELECT MONTH(o.metaData.createAt), COUNT(o) " +
            "FROM Order o WHERE o.verifyAddress = :verifyAddress " +
            "AND YEAR(o.metaData.createAt) = :year " +
            "GROUP BY MONTH(o.metaData.createAt)")
    List<Object[]> countOrdersByMonth(@Param("verifyAddress") String verifyAddress,
                                      @Param("year") int year);

    @Query("SELECT COUNT(o) FROM Order o WHERE o.verifyAddress = :verifyAddress AND o.metaData.createAt BETWEEN :startDate AND :endDate")
    Long countByVerifyAddressAndMetaData_CreateAtBetween(
            @Param("verifyAddress") String verifyAddress,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );

    @Query("SELECT COUNT(o) FROM Order o WHERE o.verifyAddress = :verifyAddress")
    long countOrdersByVerifier(@Param("verifyAddress") String verifyAddress);

    @Query("SELECT COUNT(o) FROM Order o WHERE o.isTransfer = true")
    long countTransferredOrders();

    @Query("SELECT COUNT(o) FROM Order o WHERE o.isTransfer = false")
    long countNonTransferredOrders();

    @Query("SELECT COUNT(o) FROM Order o WHERE o.isTransfer = true AND o.verifyAddress = :verifyAddress")
    long countTransferredOrdersByVerifier(String verifyAddress);

    @Query("SELECT COUNT(o) FROM Order o WHERE o.isTransfer = false AND o.verifyAddress = :verifyAddress")
    long countNonTransferredOrdersByVerifier(String verifyAddress);

    @Query("SELECT COUNT(o) FROM Order o WHERE o.metaData.createAt >= :startDate AND o.metaData.createAt < :endDate")
    Long countOrdersByWeek(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query("SELECT COUNT(o) FROM Order o WHERE o.verifyAddress = :verifyAddress AND o.status = :status")
    long countPublishedOrdersByVerifier(@Param("verifyAddress") String verifyAddress,
                                        @Param("status") Status status);



    @Query("SELECT o FROM Order o WHERE o.verifyAddress = :verifyAddress ORDER BY o.metaData.createAt DESC")
    List<Order> findTop5ByVerifyAddress(@Param("verifyAddress") String verifyAddress, Pageable pageable);

    @Query("SELECT o FROM Order o WHERE o.status = :status")
    Page<Order> findAllByStatus(@Param("status") Status status, Pageable pageable);



}
