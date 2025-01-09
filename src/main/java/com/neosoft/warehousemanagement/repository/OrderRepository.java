package com.neosoft.warehousemanagement.repository;

import com.neosoft.warehousemanagement.dto.OrderDto;
import com.neosoft.warehousemanagement.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.net.ContentHandler;

public interface OrderRepository extends JpaRepository<Order,Long> {


//    ContentHandler findTopByOrderByCreatedAtDesc(PageRequest of);
    Page<Order> findAllByOrderByCreatedAtDesc(Pageable pageable);
}
