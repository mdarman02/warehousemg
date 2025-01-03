package com.neosoft.warehousemanagement.service;
import com.neosoft.warehousemanagement.entity.Order;
import com.neosoft.warehousemanagement.entity.OrderItem;
import com.neosoft.warehousemanagement.entity.Product;
import com.neosoft.warehousemanagement.entity.StockMovement;
import com.neosoft.warehousemanagement.exception.InsufficientStockException;
import com.neosoft.warehousemanagement.repository.OrderItemRepository;
import com.neosoft.warehousemanagement.repository.OrderRepository;
import com.neosoft.warehousemanagement.repository.ProductRepository;
import com.neosoft.warehousemanagement.repository.StockMovementRepository;
import com.neosoft.warehousemanagement.request.OrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private StockMovementRepository stockMovementRepository;

    @Override
    @Transactional
    public Order createOrder(OrderRequest request) {
        // Step 1: Check stock availability
        for (OrderItem item : request.getItems()) {
            Product product = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new ProductNotFoundException("Product not found"));

            // Check if the current stock is sufficient
            if (product.getCurrentStock() < item.getQuantity()) {
                throw new InsufficientStockException("Not enough stock for product: " + product.getName());
            }
        }

        // Step 2: Create order
        Order order = new Order();
        order.setStatus("PENDING");
        order.setTotalAmount(calculateTotalAmount(request));  // Calculate the total amount
        order.setCreatedAt(LocalDateTime.now());
        order.setNotes(request.getNotes());

        // Save the order (but not the items yet)
        order = orderRepository.save(order);

        // Step 3: Save order items
        for (OrderItem item : request.getItems()) {
            Product product = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new ProductNotFoundException("Product not found"));

            item.setOrder(order);
            item.setUnitPrice(product.getPrice());  // Assuming the product price is stored in product table
            orderItemRepository.save(item);
        }

        // Step 4: Reduce the stock
        for (OrderItem item : request.getItems()) {
            removeStock(item.getProductId(), item.getQuantity(), "SALE");
        }

        // Step 5: Return the created order
        return order;
    }

    private BigDecimal calculateTotalAmount(OrderRequest request) {
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (OrderItem item : request.getItems()) {
            Product product = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new ProductNotFoundException("Product not found"));

            totalAmount = totalAmount.add(product.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
        }
        return totalAmount;
    }

    @Transactional
    public void removeStock(Long productId, int quantity, String reason) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));

        if (product.getCurrentStock() < quantity) {
            throw new InsufficientStockException("Not enough stock to complete the sale.");
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

