package com.neosoft.warehousemanagement.strategy;

import com.neosoft.warehousemanagement.entity.Product;

import java.math.BigDecimal;

public class SeasonalDiscountPricingStrategy implements PricingStrategy {

    @Override
    public BigDecimal calculatePrice(Product product, int quantity) {
        BigDecimal price = product.getPrice(); // Assume this returns BigDecimal
        BigDecimal quantityBigDecimal = BigDecimal.valueOf(quantity);
        BigDecimal discount = new BigDecimal("0.8"); // 20% discount for seasonal sales

        // Apply the discount and calculate total price
        return price.multiply(quantityBigDecimal).multiply(discount);
    }
}
