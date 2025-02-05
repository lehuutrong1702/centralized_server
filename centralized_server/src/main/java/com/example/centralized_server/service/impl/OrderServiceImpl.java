package com.example.centralized_server.service.impl;

import com.example.centralized_server.dto.OrderDto;
import com.example.centralized_server.dto.OrderRequest;
import com.example.centralized_server.entity.MetaData;
import com.example.centralized_server.entity.Order;
import com.example.centralized_server.entity.Status;
import com.example.centralized_server.entity.User;
import com.example.centralized_server.mapper.OrderMapper;
import com.example.centralized_server.repository.OrderRepository;
import com.example.centralized_server.repository.UserRepository;
import com.example.centralized_server.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final OrderMapper orderMapper;

    @Override
    public void createOrder(OrderRequest orderRequest) {
        Optional<User> user = userRepository.findByAddress(orderRequest.getUserAddress());
        System.out.println(orderRequest.getStatus());
        // Chuyển đổi OrderDto thành Order
        Order order = new Order();

        if (user.isPresent()) {
            order.setUser(user.get());
        }

        // Tạo MetaData từ OrderDto
        MetaData metaData = new MetaData();
        metaData.setName(orderRequest.getMetaData().getName());
        metaData.setApplicationForm(orderRequest.getMetaData().getApplicationForm());
        metaData.setSamples(orderRequest.getMetaData().getSamples());
        metaData.setCreateAt(LocalDateTime.now());
        metaData.setOrder(order);

        order.setMetaData(metaData);
        order.setStatus(orderRequest.getStatus());

        // Lưu Order vào database
        orderRepository.save(order);
    }

    @Override
    public List<OrderDto> getAllOrdersByAddress(String address) {
        return List.of();
    }

    @Override
    public List<OrderDto> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        List<OrderDto> orderDtos = new ArrayList<>();

        for (Order order : orders) {
            OrderDto orderDto = orderMapper.toOrderDTO(order);
            orderDtos.add(orderDto);
        }

        return orderDtos;
    }

    @Override
    public List<OrderDto> getOrdersByStatus(Status status) {
        List<Order> orders = orderRepository.findByStatus(status);
        List<OrderDto> orderDtos = new ArrayList<>();

        for (Order order : orders) {
            OrderDto orderDto = orderMapper.toOrderDTO(order);
            orderDtos.add(orderDto);
        }

        return orderDtos;
    }

    @Override
    public OrderDto updateOrderStatus(Long id, Status status) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setStatus(status);
        order = orderRepository.save(order);

        return orderMapper.toOrderDTO(order);
    }

    @Override
    public OrderDto getByIdOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        return orderMapper.toOrderDTO(order);
    }
}
