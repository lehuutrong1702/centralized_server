package com.example.centralized_server.service;

import com.example.centralized_server.dto.OrderDto;
import com.example.centralized_server.dto.UserDto;
import com.example.centralized_server.entity.Role;
import com.example.centralized_server.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;


import java.util.List;

public interface UserService {
    void createUser(UserDto user);

    UserDetailsService userDetailsService();

    Boolean checkAddressExist(String address);

    List<User> getAccountsByRole(String role);
    List<User> getAccountsByApprovalStatus(boolean isApprove);
    String getRoleByAddress(String address);
    boolean isAccountApproved(String address);
    void approveAccount(String address);
    List<OrderDto> getOrder(String address);
    User getUserByAddress(String address);
    List<User> getAll();

}
