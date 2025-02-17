package com.example.centralized_server.mapper;


import com.example.centralized_server.dto.UserDto;
import com.example.centralized_server.entity.User;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;


@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserDto userDto);

    UserDto toUserDTO(User user);
}
