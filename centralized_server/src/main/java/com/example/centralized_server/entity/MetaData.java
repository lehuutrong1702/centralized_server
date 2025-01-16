package com.example.centralized_server.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "meta_data")
public class MetaData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "application_form", nullable = false)
    private String applicationForm;

    @Column(name = "samples", nullable = false)
    private String samples;

    @OneToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;


}
