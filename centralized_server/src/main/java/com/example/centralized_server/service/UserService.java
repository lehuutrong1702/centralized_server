package com.example.centralized_server.service;

import com.example.centralized_server.dto.UserDto;

public interface UserService {
    void createUser(UserDto user);

    Boolean checkAddressExist(String address);
}
