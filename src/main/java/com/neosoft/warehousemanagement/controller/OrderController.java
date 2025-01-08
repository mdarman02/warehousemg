package com.neosoft.warehousemanagement.controller;

import com.neosoft.warehousemanagement.dto.OrderDto;
import com.neosoft.warehousemanagement.entity.Order;
import com.neosoft.warehousemanagement.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "http://localhost:4200") // Enable CORS for this controller
public class OrderController {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    private OrderService orderService;

    // Create new order
    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody OrderDto orderDto) {
        logger.info("Creating new order: {}",orderDto);
        Order createdOrder = orderService.createOrder(orderDto);
        return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
    }

    // Get all orders with pagination
    @GetMapping
    public ResponseEntity<Page<Order>> getOrders(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "0") int size) {
        logger.info("Fetching order - page: {}, size: {}",page,size);
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
