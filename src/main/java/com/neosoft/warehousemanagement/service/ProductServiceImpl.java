package com.neosoft.warehousemanagement.service;

import com.neosoft.warehousemanagement.dto.ProductDto;
import com.neosoft.warehousemanagement.entity.Product;
import com.neosoft.warehousemanagement.entity.StockMovement;
//import com.neosoft.warehousemanagement.enumDto.Category;
import com.neosoft.warehousemanagement.enumDto.Category;
import com.neosoft.warehousemanagement.exception.ProductAlreadyExistsException;
import com.neosoft.warehousemanagement.repository.ProductRepository;
import com.neosoft.warehousemanagement.repository.StockMovementRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService{

    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    private ProductRepository productRepository;
    private StockMovementRepository stockMovementRepository;

    public ProductServiceImpl(ProductRepository productRepository, StockMovementRepository stockMovementRepository) {
        this.productRepository = productRepository;
        this.stockMovementRepository = stockMovementRepository;
    }

    @Override
    public Page<Product> getAllProducts(int page, int size) {

        logger.info("Fetching all products - page: {}, size: {}", page, size);
        Pageable pageable = PageRequest.of(page, size);
        return productRepository.findAll(pageable);
    }

    @Override
    public Optional<Product> getProductById(Long id) {
        logger.info("Fetching product by id: {}", id);
        return productRepository.findById(id);
    }

    @Override
    public ProductDto createProduct(ProductDto productDto) {
        logger.info("Creating a new product: {}", productDto);

        Optional<Product> existingProduct = productRepository.findBySku(productDto.getSku());
        if (existingProduct.isPresent()) {
            throw new ProductAlreadyExistsException("Product with SKU " + productDto.getSku() + " already exists.");
        }

        Product product=new Product();
        product.setId(productDto.getId());
        product.setCategory(productDto.getCategory());
//        product.setCategory(Category.valueOf(productDto.getCategory()));
        product.setName(productDto.getName());
        product.setSku(productDto.getSku());
        product.setPrice(productDto.getPrice());
        product.setDescription(productDto.getDescription());

        Product saveProduct= productRepository.save(product);


        ProductDto dto=new ProductDto();
        dto.setId(saveProduct.getId());
        dto.setDescription(saveProduct.getDescription());
        dto.setName(saveProduct.getName());
        dto.setSku(saveProduct.getSku());
        dto.setPrice(saveProduct.getPrice());
//        dto.setCategory(saveProduct.getCategory());
        dto.setCategory(saveProduct.getCategory().name());  // Convert Enum to String

        return dto;
    }


    @Override
    public ProductDto createProductWithQuantity(ProductDto productDto, int quantity) {
        logger.info("Creating a new product: {}", productDto);

        Optional<Product> existingProduct = productRepository.findBySku(productDto.getSku());
        if (existingProduct.isPresent()) {
            throw new ProductAlreadyExistsException("Product with SKU " + productDto.getSku() + " already exists.");
        }

        Product product=new Product();
        product.setId(productDto.getId());
        product.setCategory(productDto.getCategory());
        product.setName(productDto.getName());
        product.setSku(productDto.getSku());
        product.setPrice(productDto.getPrice());
        product.setDescription(productDto.getDescription());

        product.setCurrentStock(quantity);

        Product saveProduct= productRepository.save(product);

//        StockMovement stock =new StockMovement();
//        stock.setProduct(saveProduct);
//        stock.setQuantity(quantity);
//        stockMovementRepository.save(stock);

//        saveProduct.setCurrentStock(quantity);

        ProductDto dto=new ProductDto();
        dto.setId(saveProduct.getId());
        dto.setDescription(saveProduct.getDescription());
        dto.setName(saveProduct.getName());
        dto.setSku(saveProduct.getSku());
        dto.setPrice(saveProduct.getPrice());
//        dto.setCategory(saveProduct.getCategory());
        dto.setCategory(saveProduct.getCategory().name());  // Convert Enum to String

        return dto;
    }


    @Override
    public Product updateProduct(Long id, Product product) {
        logger.info("Updating product with id: {}", id);
        product.setId(id);
        product.setCreatedAt(LocalDateTime.now());
        return productRepository.save(product);
    }

    @Override
    public void deleteProduct(Long id) {
        logger.info("Deleting product with id: {}", id);
        productRepository.deleteById(id);
    }

    @Override
    public long getTotalProducts() {
       long count=  productRepository.count();
        logger.info("Total products count: {}", count);
        return count;
    }

    @Override
    public double getTotalStockValue() {
        double totalStockValue= productRepository.findAll().stream()
                .mapToDouble(product -> product.getPrice()
                        .multiply(BigDecimal.valueOf(product.getCurrentStock()))
                        .doubleValue())  // Convert the BigDecimal result to double
                .sum();
        logger.info("Total stock value: {}", totalStockValue);
        return totalStockValue;
    }

    @Override
    public List<Product> getLowStockProducts() {
        return productRepository.findAll().stream()
                .filter(product -> product.getCurrentStock() < 10)
                .collect(Collectors.toList());
    }
}
