package com.neosoft.warehousemanagement.service;
import com.neosoft.warehousemanagement.dto.OrderDto;
import com.neosoft.warehousemanagement.dto.OrderItemDtO;
import com.neosoft.warehousemanagement.entity.Order;
import com.neosoft.warehousemanagement.entity.OrderItem;
import com.neosoft.warehousemanagement.entity.Product;
import com.neosoft.warehousemanagement.entity.StockMovement;
import com.neosoft.warehousemanagement.repository.OrderItemRepository;
import com.neosoft.warehousemanagement.repository.OrderRepository;
import com.neosoft.warehousemanagement.repository.ProductRepository;
import com.neosoft.warehousemanagement.repository.StockMovementRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);
    private ProductRepository productRepository;

    private OrderRepository orderRepository;

    private OrderItemRepository orderItemRepository;

    private StockMovementRepository stockMovementRepository;

    public OrderServiceImpl(ProductRepository productRepository, OrderRepository orderRepository,
                            OrderItemRepository orderItemRepository,
                            StockMovementRepository stockMovementRepository) {
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.stockMovementRepository = stockMovementRepository;
    }



    @Transactional
    @Override
    public Order createOrder(OrderDto request) {
        // Check stock availability
        for (OrderItemDtO item : request.getItems()) {
            Product product = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            // Check if the current stock is sufficient
            if (product.getCurrentStock() < item.getQuantity()) {
                throw new RuntimeException("Not enough stock for product: " + product.getName());
            }

        }

        //  Create order
        Order order = new Order();
        order.setId(request.getId());
        order.setStatus("PENDING");
        order.setTotalAmount(calculateTotalAmount(request));  // Calculate the total amount
        order.setCreatedAt(LocalDateTime.now());
        order.setNotes(request.getNotes());

        // Save the order (but not the items yet)
        Order savedorder = orderRepository.save(order);

        //  Save order items
        for (OrderItemDtO item : request.getItems()) {
            Product product = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            Order order1 = orderRepository.findById(savedorder.getId())
                    .orElseThrow(() -> new RuntimeException("Order not found"));

            // Create a new OrderItem entity and set its properties
            OrderItem orderItem = new OrderItem();
            orderItem.setId(item.getId());  // If the ID is already provided, you can set it
            orderItem.setOrder(order1);         // Set the Order entity
            orderItem.setProduct(product);     // Set the Product entity
            orderItem.setQuantity(item.getQuantity()); // Set quantity
            orderItem.setUnitPrice(item.getUnitPrice()); // Set unit price

            // Save the OrderItem (assuming you have an orderItemRepository)
            orderItemRepository.save(orderItem);

        }

        //  Reduce the stock
        for (OrderItemDtO item : request.getItems()) {
            removeStock(item.getProductId(), item.getQuantity(), "SALE");
        }


        //  Return the created order
        return savedorder;
    }

    @Override
    public Page<Order> getOrders(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return orderRepository.findAll(pageable);
    }

    @Override
    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    @Override
    public long getTotalOrders() {
            return orderRepository.count();
    }

    @Override
    public List<Order> getRecentOrders(int limit) {
        return orderRepository.findAllByOrderByCreatedAtDesc(PageRequest.of(0, limit)).getContent();
    }

    private BigDecimal calculateTotalAmount(OrderDto request) {
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (OrderItemDtO item : request.getItems()) {
            Product product = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            totalAmount = totalAmount.add(product.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
        }
        return totalAmount;
    }

    @Transactional
    public void removeStock(Long productId, int quantity, String reason) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (product.getCurrentStock() < quantity) {
            throw new RuntimeException("Not enough stock to complete the sale.");
        }

        product.setCurrentStock(product.getCurrentStock() - quantity); // Decrease stock by quantity

        // Optionally, create a stock movement record to track the change
        StockMovement movement = new StockMovement();
        movement.setProduct(product);
        movement.setQuantity(quantity);
        movement.setMovementType("OUT");  // Type: OUT because stock is being reduced
        movement.setReason(reason); // SALE in this case
        stockMovementRepository.save(movement);  // Save the stock movement

        productRepository.save(product);  // Save the updated product with reduced stock
    }
}


