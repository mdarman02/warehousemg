package com.neosoft.warehousemanagement.repository;

import com.neosoft.warehousemanagement.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem,Long> {
}
