package com.neosoft.warehousemanagement.strategy;

import com.neosoft.warehousemanagement.entity.Product;

import java.math.BigDecimal;

public class BulkDiscountPricingStrategy implements PricingStrategy {

    @Override
    public BigDecimal calculatePrice(Product product, int quantity) {
        BigDecimal price = product.getPrice(); // Assume this returns BigDecimal
        BigDecimal quantityBigDecimal = BigDecimal.valueOf(quantity);

        if (quantity > 10) {
            BigDecimal discount = new BigDecimal("0.9"); // 10% discount for bulk orders
            return price.multiply(quantityBigDecimal).multiply(discount);
        }

        return price.multiply(quantityBigDecimal);
    }
}