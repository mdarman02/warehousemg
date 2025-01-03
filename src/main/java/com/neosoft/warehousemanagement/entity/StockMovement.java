package com.neosoft.warehousemanagement.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "stock_movements")
public class StockMovement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product; // Foreign key to Product

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "reason", length = 255)
    private String reason;

    @Column(name = "movement_type", length = 10, nullable = false)
    private String movementType; // IN or OUT

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getMovementType() {
        return movementType;
    }

    public void setMovementType(String movementType) {
        this.movementType = movementType;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    // PrePersist method to automatically set the createdAt field before persisting the entity
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now(); // Set current timestamp before persisting
    }
}

