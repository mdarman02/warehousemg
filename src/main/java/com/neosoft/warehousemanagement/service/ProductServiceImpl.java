package com.neosoft.warehousemanagement.service;

import com.neosoft.warehousemanagement.dto.ProductDto;
import com.neosoft.warehousemanagement.entity.Product;
import com.neosoft.warehousemanagement.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService{

    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    private ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
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

        Product product=new Product();
        product.setId(productDto.getId());
        product.setCategory(productDto.getCategory());
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
        dto.setCategory(saveProduct.getCategory());

        return dto;
    }

    @Override
    public Product updateProduct(Long id, Product product) {
        logger.info("Updating product with id: {}", id);
        product.setId(id);
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
