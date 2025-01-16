package com.example.centralized_server.service;

import com.example.centralized_server.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {
    void createUser(UserDto user);

    UserDetailsService userDetailsService();

    Boolean checkAddressExist(String address);
}
