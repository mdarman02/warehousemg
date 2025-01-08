package com.neosoft.warehousemanagement.service;

public interface StockService {
    void addStock(Long productId, int quantity, String reason);

    void removeStock(Long productId, int quantity, String reason);

    void reduceStock(Long productId, int quantity, String reason);
}
