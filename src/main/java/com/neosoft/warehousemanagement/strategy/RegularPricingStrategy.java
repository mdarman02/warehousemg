package com.neosoft.warehousemanagement.strategy;

import com.neosoft.warehousemanagement.entity.Product;

import java.math.BigDecimal;

public class RegularPricingStrategy implements PricingStrategy {

    @Override
    public BigDecimal calculatePrice(Product product, int quantity) {
        BigDecimal price = product.getPrice();
        BigDecimal quantityBigDecimal = BigDecimal.valueOf(quantity);
        return price.multiply(quantityBigDecimal);
    }
}