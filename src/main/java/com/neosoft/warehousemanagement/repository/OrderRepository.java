package com.neosoft.warehousemanagement.repository;

import com.neosoft.warehousemanagement.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long> {
}
