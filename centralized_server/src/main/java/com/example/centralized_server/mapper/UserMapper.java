package com.example.centralized_server.mapper;

import com.example.centralized_server.dto.UserDto;
import com.example.centralized_server.entity.User;

public class UserMapper {
    public static User mapToUser(User user, UserDto userDto) {
        user.setUsername(userDto.getUsername());
        user.setAddress(userDto.getAddress());
        return user;
    }

    public static UserDto mapToUserDto(User user,UserDto userDto) {
        userDto.setUsername(user.getUsername());
        userDto.setAddress(user.getAddress());
        return  userDto;
    }
}
