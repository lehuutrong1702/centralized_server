package com.example.centralized_server.dto;

import com.example.centralized_server.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDto {
    private String username;
    private String address;
    private String email;
    private String phone;
    private Role role;
}
