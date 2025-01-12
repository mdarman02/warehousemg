package com.neosoft.warehousemanagement.controller;

import com.neosoft.warehousemanagement.dto.ErrorResponseDto;
import com.neosoft.warehousemanagement.dto.ProductDto;
import com.neosoft.warehousemanagement.entity.Product;
import com.neosoft.warehousemanagement.exception.ProductNotFoundException;
import com.neosoft.warehousemanagement.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/product")
@Tag(name="Product APIs")
@CrossOrigin(origins = "http://localhost:4200") // Enable CORS for this controller
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    @Operation(summary = "Get all product")
    public ResponseEntity<Page<Product>> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        logger.info("Fetching products - page: {}, size: {}", page, size);
        if (page < 0 || size <= 0) {
            logger.error("Invalid pagination parameters - page: {}, size: {}", page, size);
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            throw  new ProductNotFoundException("Page Size is 0");
        }
        Page<Product> products = productService.getAllProducts(page, size);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get all product by id")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        logger.info("Fetching product with id: {}", id);
        Product product = productService.getProductById(id).orElseThrow(()->new ProductNotFoundException("Product not found with id:"+id));
//        return product.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
//            Product product = productService.getProductById(id);
//                return ResponseEntity.ok(product);
        return ResponseEntity.ok(product);

    }

    @PostMapping
    @Operation(summary = "Add new Product")
    public ResponseEntity<ProductDto> createProduct(@Valid @RequestBody ProductDto productDto, @RequestParam int quantity) {

        logger.info("Creating a new product: {}", productDto);
//        ProductDto createdProduct = productService.createProduct(productDto);
        ProductDto createdProduct = productService.createProductWithQuantity(productDto,quantity);

        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }




    @PutMapping("/{id}")
    @Operation(summary = "Update product")
    public ResponseEntity<?> updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody Product product) {
        logger.info("Updating product with id: {}", id);
        if (!productService.getProductById(id).isPresent()) {
            logger.warn("Product with id: {} not found", id);
            throw  new ProductNotFoundException("Product not found with id:"+ id);
        }
        Product updatedProduct = productService.updateProduct(id, product);
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete product")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        logger.info("Deleting product with id: {}", id);
        if (!productService.getProductById(id).isPresent()) {
            logger.warn("Deleting product with id: {}", id);
            throw  new ProductNotFoundException("Product not found with id:"+ id);
        }
        productService.deleteProduct(id);
//        return ResponseEntity.noContent().build();
//        return new ResponseEntity<>("Product Deleted Successfully",HttpStatus.ACCEPTED);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/count")
    @Operation(summary = "Get Total product")
    public ResponseEntity<Long> getTotalProducts() {
        logger.info("Fetching total number of products");
        long totalProducts = productService.getTotalProducts();
//        if(totalProducts<0){
//            throw  new ProductNotFoundException("No Product:");
//        }
        return ResponseEntity.ok(totalProducts);
    }

    @GetMapping("/total-stock-value")
    @Operation(summary = "Get Total stock ")
    public ResponseEntity<Double> getTotalStockValue() {
        logger.info("Fetching total stock value");
        double totalStockValue = productService.getTotalStockValue();
        return ResponseEntity.ok(totalStockValue);
    }

    @GetMapping("/low-stock")
    @Operation(summary = "Get Low stock")
    public ResponseEntity<List<Product>> getLowStockProducts() {
        List<Product> lowStockProducts = productService.getLowStockProducts();
        return ResponseEntity.ok(lowStockProducts);
    }
}
