package com.example.centralized_server.dto;

import com.example.centralized_server.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class UserDto {
    private String username;
    private String address;
    private String email;
    private Role role;
    private Boolean isApprove;
}
