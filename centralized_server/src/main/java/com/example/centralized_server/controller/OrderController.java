package com.example.centralized_server.controller;

import com.example.centralized_server.dto.OrderDto;
import com.example.centralized_server.dto.OrderRequest;
import com.example.centralized_server.entity.Order;
import com.example.centralized_server.entity.Status;
import com.example.centralized_server.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/v1/orders")
@AllArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<Void> createOrder(@RequestBody OrderRequest orderRequest) {
        orderService.createOrder(orderRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/getAllCopyright")
    public ResponseEntity<List<OrderDto>> getAllCopyright() {
        List<OrderDto> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/getCopyrightByStatus")
    public ResponseEntity<List<OrderDto>> getCopyrightByStatus(@RequestParam Status status) {
        List<OrderDto> orders = orderService.getOrdersByStatus(status);
        return ResponseEntity.ok(orders);
    }

    @PostMapping("/updateStatus")
    public ResponseEntity<OrderDto> updateStatus(@RequestParam Long id, @RequestBody Status status) {
        System.out.println("d");
        OrderDto updatedOrder = orderService.updateOrderStatus(id, status);
        return ResponseEntity.ok(updatedOrder);
    }
    @GetMapping
    public ResponseEntity<List<OrderDto>> getByStatus(@RequestParam Boolean status) {
        return null ;
    }

    @PatchMapping("/{id}")
    public ResponseEntity<OrderDto> update(@PathVariable Long id, @RequestBody OrderDto orderDto) {
        return null ;
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> getId(@PathVariable Long id) {
        OrderDto order = orderService.getByIdOrder(id);
        return ResponseEntity.ok(order);
    }

//    @GetMapping
//    public ResponseEntity<List<OrderDto>> getByAddress(@RequestParam String address) {
//        return null ;
//    }
}
