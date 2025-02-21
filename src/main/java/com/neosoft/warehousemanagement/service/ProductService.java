package com.neosoft.warehousemanagement.service;

import com.neosoft.warehousemanagement.dto.ProductDto;
import com.neosoft.warehousemanagement.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


public interface ProductService {
     Page<Product> getAllProducts(int page, int size);
     Optional<Product> getProductById(Long id);
     ProductDto createProduct(ProductDto productDto);

     ProductDto createProductWithQuantity(ProductDto productDto, int quantity);

     Product updateProduct(Long id, Product product);
     void deleteProduct(Long id);

     long getTotalProducts();

     double getTotalStockValue();

     List<Product> getLowStockProducts();
}
