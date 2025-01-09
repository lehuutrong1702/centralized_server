package com.example.centralized_server.controller;


import com.example.centralized_server.dto.OrderDto;
import com.example.centralized_server.dto.ResponseDto;
import com.example.centralized_server.dto.UserDto;
import com.example.centralized_server.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @GetMapping
    public ResponseEntity<UserDto> getByAddress(@RequestParam String address){
        return null;
    }



    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getById(@PathVariable Long id){
        return null;
    }

    @GetMapping("/{id}/orders")
    public ResponseEntity<List<OrderDto>> getOrders(@PathVariable Long id){
        return null;
    }

    @PostMapping
    public ResponseEntity<ResponseDto> create(@RequestBody UserDto user){
        return null;
    }



}
