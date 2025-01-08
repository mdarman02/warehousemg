package com.neosoft.warehousemanagement.service;

import com.neosoft.warehousemanagement.entity.Product;
import com.neosoft.warehousemanagement.entity.StockMovement;
import com.neosoft.warehousemanagement.repository.ProductRepository;
import com.neosoft.warehousemanagement.repository.StockMovementRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StockServiceImpl implements StockService {

    private ProductRepository productRepository;
    private StockMovementRepository stockMovementRepository;

    public StockServiceImpl(ProductRepository productRepository, StockMovementRepository stockMovementRepository) {
        this.productRepository = productRepository;
        this.stockMovementRepository = stockMovementRepository;
    }

    // Method to add stock (movementType = "IN")
    @Override
    @Transactional
    public void addStock(Long productId, int quantity, String reason) {

        System.out.println(productId);

            // Fetch the product from the repository
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new IllegalArgumentException("Product not found"));
        // Ensure current stock is not null before updating
        int currentStock = product.getCurrentStock() != null ? product.getCurrentStock() : 0;

            // Create a stock movement record for adding stock (IN movement)
            StockMovement movement = new StockMovement();
            movement.setProduct(product);
            movement.setQuantity(quantity);
            movement.setReason(reason);
            movement.setMovementType("IN");

            // Save the stock movement (record the "IN" movement)
            stockMovementRepository.save(movement);

            // Update the product's current stock
            product.setCurrentStock(product.getCurrentStock() + quantity);

            // Save the updated product with the new stock level
            productRepository.save(product);
        }

        // Method to remove stock (movementType = "OUT")
        @Transactional
        @Override
        public void removeStock (Long productId,int quantity, String reason){
            // Fetch the product from the repository
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new IllegalArgumentException("Product not found"));

            // Ensure there's enough stock to remove
            if (product.getCurrentStock() < quantity) {
                throw new IllegalArgumentException("Not enough stock to remove. Current stock is less than the requested quantity.");
            }

            // Create a stock movement record for removing stock (OUT movement)
            StockMovement movement = new StockMovement();
            movement.setProduct(product);
            movement.setQuantity(quantity);
            movement.setReason(reason);
            movement.setMovementType("OUT");

            // Save the stock movement (record the "OUT" movement)
            stockMovementRepository.save(movement);

            // Update the product's current stock
            product.setCurrentStock(product.getCurrentStock() - quantity);

            // Save the updated product with the new stock level
            productRepository.save(product);
        }

    @Override
    @Transactional
    public void reduceStock(Long productId, int quantity, String reason) {
        // Fetch the product from the repository
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
        // Ensure current stock is not null before updating
        int currentStock = product.getCurrentStock() != null ? product.getCurrentStock() : 0;

        // Create a stock movement record for adding stock (IN movement)
        StockMovement movement = new StockMovement();
        movement.setProduct(product);
        movement.setQuantity(quantity);
        movement.setReason(reason);
        movement.setMovementType("OUT");

        // Save the stock movement (record the "IN" movement)
        stockMovementRepository.save(movement);

        // Update the product's current stock
        product.setCurrentStock(product.getCurrentStock() - quantity);

        // Save the updated product with the new stock level
        productRepository.save(product);
    }


//    @Transactional
//    public void updateStock(Long productId, int quantity, String reason, String movementType) {
//        // Validate movement type
//        if (!movementType.equals("IN") && !movementType.equals("OUT")) {
//            throw new IllegalArgumentException("Invalid movement type. It must be either 'IN' or 'OUT'.");
//        }
//
//        // Fetch the product from the repository
//        Product product = productRepository.findById(productId)
//                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
//
//        // Handle "IN" movement type (adding stock)
//        if (movementType.equals("IN")) {
//            // Create stock movement record
//            StockMovement movement = new StockMovement();
//            movement.setProduct(product);
//            movement.setQuantity(quantity);
//            movement.setReason(reason);
//            movement.setMovementType("IN");
//
//            // Save stock movement
//            stockMovementRepository.save(movement);
//
//            // Update product's current stock
//            product.setCurrentStock(product.getCurrentStock() + quantity);
//            productRepository.save(product);
//        }
//
//        // Handle "OUT" movement type (removing stock)
//        else if (movementType.equals("OUT")) {
//            if (product.getCurrentStock() < quantity) {
//                throw new IllegalArgumentException("Not enough stock to remove. Current stock is less than the requested quantity.");
//            }
//
//            // Create stock movement record for "OUT" type
//            StockMovement movement = new StockMovement();
//            movement.setProduct(product);
//            movement.setQuantity(quantity);
//            movement.setReason(reason);
//            movement.setMovementType("OUT");
//
//            // Save stock movement
//            stockMovementRepository.save(movement);
//
//            // Update product's current stock
//            product.setCurrentStock(product.getCurrentStock() - quantity);
//            productRepository.save(product);
//        }
//    }
}
