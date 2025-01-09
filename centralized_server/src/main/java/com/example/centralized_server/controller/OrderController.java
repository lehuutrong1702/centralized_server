package com.example.centralized_server.controller;

import com.example.centralized_server.dto.OrderDto;
import com.example.centralized_server.entity.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {


    @GetMapping
    public ResponseEntity<List<OrderDto>> getByStatus(@RequestParam Boolean status) {
        return null ;
    }

    @PatchMapping("/{id}")
    public ResponseEntity<OrderDto> update(@PathVariable Long id, @RequestBody OrderDto orderDto) {
        return null ;
    }
}
