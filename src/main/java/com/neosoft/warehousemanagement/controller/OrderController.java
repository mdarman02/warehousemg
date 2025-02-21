package com.neosoft.warehousemanagement.controller;

import com.neosoft.warehousemanagement.dto.OrderDto;
import com.neosoft.warehousemanagement.entity.Order;
import com.neosoft.warehousemanagement.exception.ProductNotFoundException;
import com.neosoft.warehousemanagement.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/orders")
@Tag(name = "0rder APIs")
@CrossOrigin(origins = "http://localhost:4200") // Enable CORS for this controller
public class OrderController {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    private OrderService orderService;

    // Create new order
    @PostMapping
    @Operation(summary = "Add Order")
    public ResponseEntity<Order> createOrder(@RequestBody OrderDto orderDto) {
        logger.info("Creating new order: {}",orderDto);
        Order createdOrder = orderService.createOrder(orderDto);
        return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
    }


    @Operation(summary = "Show recent order")
    @GetMapping("/recent")
    public List<Order> getRecentOrders(@RequestParam int limit) {
        return orderService.getRecentOrders(limit);
    }

    @GetMapping("/count")
    @Operation(summary = "Total Order")
    public ResponseEntity<Long> getTotalOrders() {
        logger.info("Fetching total number of products");
        long totalProducts = orderService.getTotalOrders();
        return ResponseEntity.ok(totalProducts);
    }

    // Get all orders with pagination
    @GetMapping
    @Operation(summary = "Get all orders")
    public ResponseEntity<Page<Order>> getOrders(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        logger.info("Fetching order - page: {}, size: {}",page,size);
        if (page < 0 || size <= 0) {
            logger.error("Invalid pagination parameters - page: {}, size: {}", page, size);
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            throw  new ProductNotFoundException("Page Size is 0");
        }
        Page<Order> orders = orderService.getOrders(page, size);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    // Get order by ID
    @GetMapping("/{id}")
    @Operation(summary = "Get order by Id")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
//        Optional<Order> order = orderService.getOrderById(id);
//        return order != null ? new ResponseEntity<>(order, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
          Order order=orderService.getOrderById(id).orElseThrow(()->new ProductNotFoundException("Product not found with id: "+ id));
          return ResponseEntity.ok(order);
    }
}
