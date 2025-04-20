package com.example.centralized_server.dto;

import com.example.centralized_server.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String username;
    private String address;
    private String email;
    private Role role;
    private Boolean isApprove;
    private Boolean isStaff;
    private Long idVerifier;  // Thay đổi kiểu từ String sang Long
}
