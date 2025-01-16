package com.example.centralized_server.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class MetaDataDto {

    private String applicationForm;
    private String samples;

}
