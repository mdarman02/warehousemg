package com.neosoft.warehousemanagement.repository;

import com.neosoft.warehousemanagement.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product,Long> {
    Optional<Product> findBySku(String sku);
}
