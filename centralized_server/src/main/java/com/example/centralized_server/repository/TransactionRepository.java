package com.example.centralized_server.repository;

import com.example.centralized_server.entity.Order;
import com.example.centralized_server.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long>,
        JpaSpecificationExecutor<Transaction> {
    List<Transaction> findByVerifyAddress(String address);
    List<Transaction> findByToUserId(Long toUser);

    @Query("SELECT MONTH(o.createAt), COUNT(o) FROM Transaction o WHERE YEAR(o.createAt) = :year GROUP BY MONTH(o.createAt)")
    List<Object[]> countTransactionsPerMonth(@Param("year") int year);
}
