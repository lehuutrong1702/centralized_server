package com.example.centralized_server.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "meta_data")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MetaData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "application_form", nullable = false)
    private String applicationForm;

    @Column(name = "samples", nullable = false)
    private String samples;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createAt;


    @OneToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @PrePersist
    protected void onCreate() {
        this.createAt = LocalDateTime.now();
    }
}
