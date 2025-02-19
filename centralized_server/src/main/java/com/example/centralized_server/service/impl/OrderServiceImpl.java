package com.example.centralized_server.service.impl;

import com.example.centralized_server.dto.MetaDataDto;
import com.example.centralized_server.dto.OrderDto;
import com.example.centralized_server.dto.OrderRequest;
import com.example.centralized_server.entity.MetaData;
import com.example.centralized_server.entity.Order;
import com.example.centralized_server.entity.Status;
import com.example.centralized_server.entity.User;
import com.example.centralized_server.mapper.OrderMapper;
import com.example.centralized_server.repository.MetaDataRepository;
import com.example.centralized_server.repository.OrderRepository;
import com.example.centralized_server.repository.UserRepository;
import com.example.centralized_server.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final OrderMapper orderMapper;
    private final MetaDataRepository metaDataRepository;

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
        metaData.setUri(orderRequest.getMetaData().getUri());
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
        User user = userRepository.findByAddress(address)
                .orElseThrow(() -> new RuntimeException("User not found"));;


        List<Order> orders = orderRepository.findByUserId(user.getId());
        return orders.stream().map(orderMapper::toOrderDTO).collect(Collectors.toList());
    }

    @Override
    public List<OrderDto> getAllOrders(Status status, String uri, String address) {
        if (status == null && uri == null) {
            List<Order> orders = orderRepository.findAll();
            List<OrderDto> orderDtos = new ArrayList<>();

            for (Order order : orders) {
                OrderDto orderDto = orderMapper.toOrderDTO(order);
                orderDtos.add(orderDto);
            }
            return orderDtos;
        }

        if (uri != null) {
            Optional<MetaData> metaData = metaDataRepository.findByUri(uri);
            if (metaData.isPresent()) {
                OrderDto orderDto =  orderMapper.toOrderDTO(metaData.get().getOrder());
                return List.of(orderDto);
            }
        }


        List<Order> orders = new ArrayList<>();
        if (status == null && address == null) {
            orders =  orderRepository.findAll(); // Trả về tất cả bản ghi nếu cả hai đều null
        } else if (status == null) {
         return getAllOrdersByAddress(address);
        } else if (address == null) {
            orders =  orderRepository.findByStatus(status);
        } else {
            orders = orderRepository.findByStatusAndUser_Address(status, address);
        }

        return orders.stream().map(orderMapper::toOrderDTO).collect(Collectors.toList());

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

    @Override
    public OrderDto updateTokenId(Long id, Long tokenId) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setTokenId(tokenId);
        order = orderRepository.save(order);

        return orderMapper.toOrderDTO(order);
    }

    @Override
    public OrderDto getByURI(String uri) {
        MetaData metaData = metaDataRepository.findByUri(uri)
                .orElseThrow(() -> new RuntimeException("MetaData not found for URI: " + uri));
        return orderMapper.toOrderDTO(metaData.getOrder());
    }

    @Override
    public List<OrderDto> getOrdersByUserId(Long userId) {
        List<Order> orders = orderRepository.findByUserId(userId);
        return orders.stream().map(orderMapper::toOrderDTO).collect(Collectors.toList());
    }

    @Override
    public OrderDto updateMetaData(Long orderId, MetaDataDto metaDataDto) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        MetaData metaData = order.getMetaData();
        if (metaData == null) {
            throw new RuntimeException("Metadata not found for this order");
        }
        metaData.setUri(metaDataDto.getUri());
        metaData.setSamples(metaDataDto.getSamples());
        metaData.setApplicationForm(metaDataDto.getApplicationForm());

        metaDataRepository.save(metaData);

        return orderMapper.toOrderDTO(order);
    }

//    public Order update(OrderDto orderDto){
//        // get by id => order
//
//    } //

}
