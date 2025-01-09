package com.example.centralized_server.service.impl;

import com.example.centralized_server.dto.UserDto;
import com.example.centralized_server.entity.User;
import com.example.centralized_server.exception.ResourceAlreadyExistException;
import com.example.centralized_server.mapper.UserMapper;
import com.example.centralized_server.repository.UserRepository;
import com.example.centralized_server.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public void createUser(UserDto userDto) {
       User user = UserMapper.mapToUser(new User(),userDto);
        Optional<User> optionalUser = userRepository.findByAddress(user.getAddress());
        if(optionalUser.isPresent()) {
            throw new ResourceAlreadyExistException(
                    "User has address" + user.getAddress() + " already exists");
        }
       userRepository.save(user);
    }
}
