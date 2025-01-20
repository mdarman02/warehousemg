package com.neosoft.warehousemanagement.strategy;

import com.neosoft.warehousemanagement.entity.Product;

import java.math.BigDecimal;

public interface PricingStrategy {
    BigDecimal calculatePrice(Product product, int quantity);
}
