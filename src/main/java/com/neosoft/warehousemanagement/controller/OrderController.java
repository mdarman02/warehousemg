package com.neosoft.warehousemanagement.controller;

import com.neosoft.warehousemanagement.dto.OrderDto;
import com.neosoft.warehousemanagement.entity.Order;
import com.neosoft.warehousemanagement.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    private OrderService orderService;

    // Create new order
    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody OrderDto orderDto) {
        Order createdOrder = orderService.createOrder(orderDto);
        return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
    }

    // Get all orders with pagination
    @GetMapping
    public ResponseEntity<Page<Order>> getOrders(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "0") int size) {
        Page<Order> orders = orderService.getOrders(page, size);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    // Get order by ID
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Order>> getOrderById(@PathVariable Long id) {
        Optional<Order> order = orderService.getOrderById(id);
        return order != null ? new ResponseEntity<>(order, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
