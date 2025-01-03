package com.neosoft.warehousemanagement.repository;

import com.neosoft.warehousemanagement.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {
}
