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
//
//    @GetMapping()
//    public ResponseEntity<List<OrderDto>> getAllCopyright() {
//        List<OrderDto> orders = orderService.getAllOrders();
//        return ResponseEntity.ok(orders);
//    }


    @GetMapping()
    public ResponseEntity<List<OrderDto>> getCopyrightByStatus(@RequestParam Status status,
                                                               @RequestParam String uri
                                                               ) {
        List<OrderDto> orders = orderService.getOrdersByStatus(status);

        OrderDto order = orderService.getByURI(uri);

        return ResponseEntity.ok(orders);
    }

    @PatchMapping()
    public ResponseEntity<OrderDto> update(@RequestBody OrderDto orderDto) {
        OrderDto updateOrder = null;
        updateOrder = orderService.update(orderDto);
        return ResponseEntity.ok(updateOrder);
    }


    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> getId(@PathVariable Long id) {
        OrderDto order = orderService.getByIdOrder(id);
        return ResponseEntity.ok(order);
    }


//    @PatchMapping("/updateTokenId/{id}")
//    public ResponseEntity<OrderDto> updateTokenId(@PathVariable String id, @RequestParam String tokenId) {
//        OrderDto order = orderService.updateTokenId(Long.valueOf(id), Long.valueOf(tokenId));
//        return ResponseEntity.ok(order);
//    }

//    @GetMapping("/getOrderByUri")
//    public ResponseEntity<OrderDto> getOrderByUri(@RequestBody UriDTO uri) {
//        OrderDto order = orderService.getByURI(uri.getUri());
//        return ResponseEntity.ok(order);
//    }

//    @GetMapping("/getNftsByAddress/{address}")
//    public ResponseEntity<List<OrderDto>> getByUserId(@PathVariable String address) {
//        List<OrderDto> orderDtos = orderService.getAllOrdersByAddress(address);
//        return ResponseEntity.ok(orderDtos);
//    }

//    @PutMapping("/updateMetaData/{id}")
//    public ResponseEntity<OrderDto> updateMetaData(@PathVariable String id, @RequestBody MetaDataDto metaDataDto) {
//        OrderDto orderDto = orderService.updateMetaData(Long.valueOf(id), metaDataDto);
//        return ResponseEntity.ok(orderDto);
//    }
}
