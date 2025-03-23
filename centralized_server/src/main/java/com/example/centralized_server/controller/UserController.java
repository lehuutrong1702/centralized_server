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
    public ResponseEntity<User> getByAddress(@RequestParam(required = false) String address){

        return ResponseEntity.ok(userService.getUserByAddress(address));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<User>> getAll(){

        return ResponseEntity.ok(userService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getById(@PathVariable Long id){
        return null;
    }

    @GetMapping("/{address}/orders")
    public ResponseEntity<List<OrderDto>> getOrders(@PathVariable String address) {
        return ResponseEntity.ok(userService.getOrder(address));
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


    @GetMapping("/by-role")
    public ResponseEntity<List<User>> getAccountsByRole(@RequestParam String role) {
        List<User> users = userService.getAccountsByRole(role);
        return ResponseEntity.ok(users);
    } // => use parameter instead


    @GetMapping("/approvals")
    public ResponseEntity<List<User>> getAccountApprovals() {
        List<User> users = userService.getAccountsByApprovalStatus(true);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/pending-approvals")
    public ResponseEntity<List<User>> getPendingApprovals() {
        List<User> users = userService.getAccountsByApprovalStatus(false);
        return ResponseEntity.ok(users);
    } // => use parameter


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


    @PostMapping("/{address}/approved")
    public void AccountApproved(@PathVariable String address) {
        userService.approveAccount(address);
    }
    // get by address => get all information


    @GetMapping("/checkAddressExists")
    public Boolean checkAddressExists(@RequestParam String address){
        return authenticationService.checkAddressExist(address);
    }


}
