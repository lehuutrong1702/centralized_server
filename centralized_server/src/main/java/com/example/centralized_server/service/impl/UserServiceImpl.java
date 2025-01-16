package com.example.centralized_server.service.impl;

import com.example.centralized_server.dto.UserDto;
import com.example.centralized_server.entity.User;
import com.example.centralized_server.exception.ResourceAlreadyExistException;
import com.example.centralized_server.mapper.UserMapper;
import com.example.centralized_server.repository.UserRepository;
import com.example.centralized_server.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;


    @Override
    public void createUser(UserDto userDto) {

    }

    @Override
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                return userRepository.findByEmail(username).orElseThrow(
                        () -> new UsernameNotFoundException(username + "not found"));
            }
        };
    }

    @Override
    public Boolean checkAddressExist(String address) {
        return userRepository.existsByAddress(address);
    }
}
