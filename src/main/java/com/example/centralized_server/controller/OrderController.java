package com.example.centralized_server.controller;

import com.example.centralized_server.dto.*;
import com.example.centralized_server.entity.Order;
import com.example.centralized_server.entity.Status;
import com.example.centralized_server.service.OrderService;
import com.example.centralized_server.service.UserService;
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
    private final UserService userService;
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

    @PostMapping("/updateStatus/{id}")
    public ResponseEntity<OrderDto> updateStatus(@PathVariable Long id, @RequestBody StatusRequest status) {
        System.out.println("d");
        OrderDto updatedOrder = orderService.updateOrderStatus(id, status.getStatus());
        return ResponseEntity.ok(updatedOrder);
    }


    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> getId(@PathVariable Long id) {
        OrderDto order = orderService.getByIdOrder(id);
        return ResponseEntity.ok(order);
    }


    @PatchMapping("/updateTokenId/{id}")
    public ResponseEntity<OrderDto> updateTokenId(@PathVariable Long id, @RequestParam String tokenId) {
        OrderDto order = orderService.updateTokenId(id, Long.valueOf(tokenId));
        return ResponseEntity.ok(order);
    }

    @GetMapping("/getOrderByUri")
    public ResponseEntity<OrderDto> getOrderByUri(@RequestParam String uri) {
        System.out.println(uri);
        OrderDto order = orderService.getByURI(uri);
        return ResponseEntity.ok(order);
    }

    @GetMapping("/getNftsByAddress/{address}")
    public ResponseEntity<List<OrderDto>> getByUserId(@PathVariable String address) {
        List<OrderDto> orderDtos = orderService.getAllOrdersByAddress(address);
        return ResponseEntity.ok(orderDtos);
    }

    @PutMapping("/updateMetaData/{id}")
    public ResponseEntity<OrderDto> updateMetaData(@PathVariable String id, @RequestBody MetaDataDto metaDataDto) {
        OrderDto orderDto = orderService.updateMetaData(Long.valueOf(id), metaDataDto);
        return ResponseEntity.ok(orderDto);
    }
}
