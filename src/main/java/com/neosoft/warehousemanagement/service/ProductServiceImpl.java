package com.neosoft.warehousemanagement.service;

import com.neosoft.warehousemanagement.dto.ProductDto;
import com.neosoft.warehousemanagement.entity.Product;
import com.neosoft.warehousemanagement.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService{

    private ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Page<Product> getAllProducts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return productRepository.findAll(pageable);
    }

    @Override
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public ProductDto createProduct(ProductDto productDto) {

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
        product.setId(id);
        return productRepository.save(product);
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
