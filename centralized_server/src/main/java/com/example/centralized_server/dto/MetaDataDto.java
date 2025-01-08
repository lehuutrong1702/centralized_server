package com.example.centralized_server.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class MetaDataDto {

    private String title;

    private String author;

    private String copyrightClaimant;

    private LocalDate dateOfCreation;

    private LocalDate dateOfPublication;

    private String workType;

    private String copyrightRegistrationNumber;

    private LocalDate copyrightRegistrationDate;

    private boolean derivativeWork;

    private String publicDisplayDistribution;

}
