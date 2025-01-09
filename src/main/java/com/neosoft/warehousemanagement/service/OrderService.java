package com.neosoft.warehousemanagement.service;

import com.neosoft.warehousemanagement.dto.OrderDto;
import com.neosoft.warehousemanagement.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    Order createOrder(OrderDto request);

    Page<Order> getOrders(int page, int size);

    Optional<Order> getOrderById(Long id);

    long getTotalOrders();

    List<Order> getRecentOrders(int limit);
}
