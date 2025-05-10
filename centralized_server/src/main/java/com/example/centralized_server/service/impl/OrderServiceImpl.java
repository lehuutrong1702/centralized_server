package com.example.centralized_server.service.impl;

import com.example.centralized_server.dto.MetaDataDto;
import com.example.centralized_server.dto.OrderDto;
import com.example.centralized_server.dto.OrderRequest;
import com.example.centralized_server.dto.SearchCriteria;
import com.example.centralized_server.entity.MetaData;
import com.example.centralized_server.entity.Order;
import com.example.centralized_server.entity.Status;
import com.example.centralized_server.entity.User;
import com.example.centralized_server.mapper.OrderMapper;
import com.example.centralized_server.repository.MetaDataRepository;
import com.example.centralized_server.repository.OrderRepository;
import com.example.centralized_server.repository.UserRepository;
import com.example.centralized_server.service.OrderService;
import com.example.centralized_server.utils.CustomSpecification;
import com.example.centralized_server.utils.DateTimeConverter;
import org.springframework.data.domain.Page;

import com.google.type.DateTime;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.example.centralized_server.service.impl.TransactionServiceImpl.getLongs;

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
        Optional<User> verifier = userRepository.findByAddress(orderRequest.getVerifierAddress());
        // Chuyển đổi OrderDto thành Order
        Order order = new Order();

        if (user.isPresent() && verifier.isPresent()) {
            order.setUser(user.get());
            order.setVerifyAddress(verifier.get().getAddress());
        }

        // Tạo MetaData từ OrderDto
        MetaData metaData = new MetaData();
        metaData.setUri(orderRequest.getMetaData().getUri());
        metaData.setName(orderRequest.getMetaData().getName());
        metaData.setApplicationForm(orderRequest.getMetaData().getApplicationForm());
        metaData.setSamples(orderRequest.getMetaData().getSamples());

        metaData.setOrder(order);

        order.setMetaData(metaData);
        order.setStatus(orderRequest.getStatus());

        // Lưu Order vào database
        orderRepository.save(order);
    }

    @Override
    public List<OrderDto> getAllOrdersByAddress(String address) {
        User user = userRepository.findByAddress(address)
                .orElseThrow(() -> new RuntimeException("User not found"));



        List<Order> orders = orderRepository.findByUserId(user.getId());
        return orders.stream().map(orderMapper::toOrderDTO).collect(Collectors.toList());
    }

    @Override
    public List<OrderDto> getAllOrders(String search) {
        if (search == null){
            return orderRepository.findAll().stream().map(orderMapper::toOrderDTO).collect(Collectors.toList());
        }

        Pattern pattern = Pattern.compile("(.*?)([<>:])(.*)");
        Matcher matcher = pattern.matcher(search);
        if(matcher.matches()){
            Specification<Order> spec = new CustomSpecification<Order>(
                    new SearchCriteria(matcher.group(1),
                            matcher.group(2),
                            matcher.group(3)));
            return orderRepository.findAll(spec).stream().map(orderMapper::toOrderDTO).collect(Collectors.toList());
        }


        return null;





    }



    @Override
    public List<OrderDto> getOrdersByStatus(Status status, String name, LocalDateTime startDate, LocalDateTime endDate, String verifierAddress) {
        List<Order> orders = orderRepository.findByStatus(status);
        List<OrderDto> orderDtos = new ArrayList<>();

        for (Order order : orders) {
            boolean matchesName = (name == null || name.isBlank()) || order.getMetaData().getName().toLowerCase().contains(name.toLowerCase());
            boolean matchesDate = (startDate == null || !order.getMetaData().getCreateAt().isBefore(startDate)) &&
                    (endDate == null || !order.getMetaData().getCreateAt().isAfter(endDate));
            boolean matchesAddress = (verifierAddress == null || verifierAddress.isBlank())
                    || (order.getVerifyAddress() != null && order.getVerifyAddress().toLowerCase().contains(verifierAddress.toLowerCase()));

            if (matchesName && matchesDate && matchesAddress) {
                OrderDto orderDto = orderMapper.toOrderDTO(order);
                orderDtos.add(orderDto);
            }
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

    @Override
    public OrderDto update(OrderDto orderDto) {
        Optional<Order> optional = orderRepository.findById(orderDto.getId());
        if (optional.isPresent()) {
            Order order = optional.get();
            if (orderDto.getUser() != null && orderDto.getUser().getId() != null) {
                User user = userRepository.findById(orderDto.getUser().getId())
                        .orElseThrow(() -> new RuntimeException("User not found"));
                order.setUser(user);
            }


            if(order.getGasFee() != null && orderDto.getGasFee() != null){
                BigDecimal previousGas = order.getGasFee();
                order.setGasFee(previousGas.add(orderDto.getGasFee()));
                Order order1 = orderRepository.save(order);
                return orderMapper.toOrderDTO(order1);
            }

            orderMapper.updateOrderFromOrderDto(order, orderDto);

            Order order1 = orderRepository.save(order);

            MetaData metaData = order1.getMetaData();

            metaData.setUpdateAt(LocalDateTime.now());

            order1.setMetaData(metaData);

            orderRepository.save(order1);

            return orderMapper.toOrderDTO(order1);
        } else {
            throw new RuntimeException("Order not found");
        }
    }

    @Override
    public Long count(String search) {
        Pattern pattern = Pattern.compile("(.*?)([<>:])(.*)");
        Matcher matcher = pattern.matcher(search);
        if(matcher.matches()){
            Specification<Order> spec = new CustomSpecification<Order>(
                    new SearchCriteria(matcher.group(1),
                            matcher.group(2),
                            matcher.group(3)));
            return orderRepository.count(spec);
        } else {
            throw new RuntimeException("Search not found");
        }

    }

    @Override
    public long[] getMonthlyOrderCount(int year) {
        List<Object[]> result = orderRepository.countOrdersPerMonth(year);
        return getLongs(result);
    }

    @Override
    public Page<OrderDto> getOrders(Status status, String name, LocalDateTime startDate, LocalDateTime endDate, int page, int size, String address) {
        // Retrieve orders by status with pagination
        Page<Order> ordersPage = orderRepository.findAllByStatus(status, PageRequest.of(page, size));

        // Filter the orders by the date range, name, and verifyAddress if specified
        List<Order> filteredOrders = ordersPage.getContent().stream()
                .filter(order -> {
                    boolean matchesName = (name == null || name.isBlank())
                            || (order.getMetaData() != null && order.getMetaData().getName() != null
                            && order.getMetaData().getName().toLowerCase().contains(name.toLowerCase()));

                    LocalDateTime createAt = (order.getMetaData() != null) ? order.getMetaData().getCreateAt() : null;
                    boolean isAfterStart = (startDate == null) || (createAt != null && !createAt.isBefore(startDate));
                    boolean isBeforeEnd = (endDate == null) || (createAt != null && !createAt.isAfter(endDate));

                    boolean matchesAddress = (address == null || address.isBlank())
                            || (order.getVerifyAddress() != null && order.getVerifyAddress().toLowerCase().contains(address.toLowerCase()));

                    return matchesName && isAfterStart && isBeforeEnd && matchesAddress;
                })
                .collect(Collectors.toList());

        // Map the filtered orders to OrderDto
        List<OrderDto> orderDtos = filteredOrders.stream()
                .map(orderMapper::toOrderDTO)
                .collect(Collectors.toList());

        // Return the paginated result
        return new PageImpl<>(orderDtos, ordersPage.getPageable(), ordersPage.getTotalElements());
    }




}


