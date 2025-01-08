package com.neosoft.warehousemanagement.controller;

import com.neosoft.warehousemanagement.dto.ProductDto;
import com.neosoft.warehousemanagement.entity.Product;
import com.neosoft.warehousemanagement.service.ProductService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/product")
@CrossOrigin(origins = "http://localhost:4200") // Enable CORS for this controller
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<Page<Product>> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        logger.info("Fetching products - page: {}, size: {}", page, size);
        if (page < 0 || size <= 0) {
            logger.error("Invalid pagination parameters - page: {}, size: {}", page, size);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Page<Product> products = productService.getAllProducts(page, size);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        logger.info("Fetching product with id: {}", id);
        Optional<Product> product = productService.getProductById(id);
        return product.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
//            Product product = productService.getProductById(id);
//                return ResponseEntity.ok(product);


    }

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@Valid @RequestBody ProductDto productDto) {

        logger.info("Creating a new product: {}", productDto);
        ProductDto createdProduct = productService.createProduct(productDto);
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody Product product) {
        logger.info("Updating product with id: {}", id);
        if (!productService.getProductById(id).isPresent()) {
            logger.warn("Product with id: {} not found", id);
            return ResponseEntity.notFound().build();
        }
        Product updatedProduct = productService.updateProduct(id, product);
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        logger.info("Deleting product with id: {}", id);
        if (!productService.getProductById(id).isPresent()) {
            logger.warn("Deleting product with id: {}", id);
            return ResponseEntity.notFound().build();
        }
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/count")
    public ResponseEntity<Long> getTotalProducts() {
        logger.info("Fetching total number of products");
        long totalProducts = productService.getTotalProducts();
        return ResponseEntity.ok(totalProducts);
    }

    @GetMapping("/total-stock-value")
    public ResponseEntity<Double> getTotalStockValue() {
        logger.info("Fetching total stock value");
        double totalStockValue = productService.getTotalStockValue();
        return ResponseEntity.ok(totalStockValue);
    }
}
