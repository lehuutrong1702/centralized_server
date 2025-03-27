package com.example.centralized_server.service.impl;

import com.example.centralized_server.dto.OrderDto;
import com.example.centralized_server.dto.UserDto;
import com.example.centralized_server.entity.Role;
import com.example.centralized_server.entity.User;
import com.example.centralized_server.mapper.OrderMapper;
import com.example.centralized_server.mapper.UserMapper;
import com.example.centralized_server.repository.UserRepository;
import com.example.centralized_server.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.centralized_server.service.impl.TransactionServiceImpl.getLongs;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final OrderMapper orderMapper;
    private final UserMapper userMapper;
    @Override
    public void createUser(UserDto userDto) {
        User user = new User();
        user.setUsername(user.getUsername());
        user.setEmail(userDto.getEmail());
        user.setRole(userDto.getRole());
        user.setCreateAt(LocalDateTime.now());
        user.setIsApprove(false);
        userRepository.save(user);
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

    @Override
    public List<User> getAccountsByRole(String role) {
        try {
            Role roleEnum = Role.valueOf(role.toUpperCase()); // Chuyển đổi String sang Enum
            return userRepository.findByRole(roleEnum);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid role: " + role);
        }
    }

    public List<User> getAccountsByApprovalStatus(boolean isApprove) {
        List<User> users = userRepository.findByIsApprove(isApprove);
        return users;
    }

    public String getRoleByAddress(String address) {
        User user = userRepository.findByAddress(address)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        return user.getRole().name();
    }
    public boolean isAccountApproved(String address) {
        User user = userRepository.findByAddress(address)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        return Boolean.TRUE.equals(user.getIsApprove());
    }
    public void approveAccount(String address) {
        User user = userRepository.findByAddress(address)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        user.setIsApprove(true);
        userRepository.save(user);
    }

    @Override
    public List<OrderDto> getOrder(String address) {
        User user = userRepository.findByAddress(address)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        return user.getOrders().stream().map(orderMapper::toOrderDTO).collect(Collectors.toList());
    }

    @Override
    public User getUserByAddress(String address) {
        User user = userRepository.findByAddress(address)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        return user;
    }

    @Override
    public List<User> getAll() {
        List<User> users = userRepository.findAll();
        return users;
    }

    @Override
    public long[] getMonthlyUsersCount(int year) {
        List<Object[]> result = userRepository.countUsersPerMonth((year));
        return getLongs(result);
    }
}
