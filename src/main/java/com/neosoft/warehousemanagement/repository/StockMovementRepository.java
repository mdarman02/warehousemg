package com.neosoft.warehousemanagement.repository;

import com.neosoft.warehousemanagement.entity.StockMovement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockMovementRepository extends JpaRepository<StockMovement,Long> {
}
