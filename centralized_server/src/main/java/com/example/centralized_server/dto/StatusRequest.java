package com.example.centralized_server.dto;

import com.example.centralized_server.entity.Status;
import lombok.Data;

@Data
public class StatusRequest {
    private String id;
    private Status status;

}
