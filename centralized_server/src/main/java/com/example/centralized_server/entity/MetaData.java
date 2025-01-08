package com.example.centralized_server.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class MetaData {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;

    private String author;

    @Column(name= "copyright_claimant", nullable = false)
    private String copyrightClaimant;

    @Column(name= "date_of_creation", nullable = false)
    private LocalDate dateOfCreation;

    @Column(name = "date_of_publication", nullable = false)
    private LocalDate dateOfPublication;

    @Column(name="work_type", nullable = false)
    private String workType;

    @Column(name = "copyright_registration_number",nullable = false)
    private String copyrightRegistrationNumber;

    @Column(name="copyright_registration_date", nullable = false)
    private LocalDate copyrightRegistrationDate;

    @Column(name="derivative_work",nullable = false)
    private boolean derivativeWork;

    @Column(name = "public_display_distribution",nullable = false)
    private String publicDisplayDistribution;


    @OneToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;


}
