package com.example.centralized_server.repository;

import com.example.centralized_server.entity.MetaData;
import com.example.centralized_server.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MetaDataRepository extends JpaRepository<MetaData, Long> {
    Optional<MetaData> findByUri(String uri);
}
