package com.example.centralized_server.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class MetaDataDto {

    private String name;
    private String applicationForm;
    private String samples;
    private LocalDateTime createAt;

}
