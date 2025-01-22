package com.example.centralized_server.controller;


import com.example.centralized_server.dto.OrderDto;
import com.example.centralized_server.dto.ResponseDto;
import com.example.centralized_server.dto.UserDto;
import com.example.centralized_server.entity.Role;
import com.example.centralized_server.entity.User;
import com.example.centralized_server.service.AuthenticationService;
import com.example.centralized_server.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/v1/users")
@AllArgsConstructor
public class UserController {
    private final UserService userService;
    private AuthenticationService authenticationService;
    @GetMapping
    public ResponseEntity<UserDto> getByAddress(@RequestParam String address){
        return null;
    }



    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getById(@PathVariable Long id){
        return null;
    }

    @GetMapping("/{id}/orders")
    public ResponseEntity<List<OrderDto>> getOrders(@PathVariable Long id) {
        return null;
    }



    @PostMapping
    public ResponseEntity<ResponseDto> create(@RequestBody UserDto user){
        System.out.println(user.getEmail());
        userService.createUser(user);

        return ResponseEntity.ok(new ResponseDto(
                "201",
                "Create user successfully"
        ));
    }
    @GetMapping("/role/{role}")
    public ResponseEntity<List<User>> getAccountsByRole(@PathVariable Role role) {
        List<User> users = userService.getAccountsByRole(role);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/pending-approvals")
    public ResponseEntity<List<User>> getPendingApprovals() {
        List<User> users = userService.getAccountsByApprovalStatus(false);
        return ResponseEntity.ok(users);
    }
    @GetMapping("/{address}/role")
    public ResponseEntity<String> getRoleByAddress(@PathVariable String address) {
        String role = userService.getRoleByAddress(address);
        return ResponseEntity.ok(role);
    }
    @GetMapping("/{address}/is-approved")
    public ResponseEntity<Boolean> isAccountApproved(@PathVariable String address) {
        boolean isApproved = userService.isAccountApproved(address);
        return ResponseEntity.ok(isApproved);
    }

    @GetMapping("/checkAddressExists")
    public Boolean checkAddressExists(@RequestParam String address){
        return authenticationService.checkAddressExist(address);
    }   
}
