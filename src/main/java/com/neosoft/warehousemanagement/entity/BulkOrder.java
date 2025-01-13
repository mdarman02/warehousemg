package com.neosoft.warehousemanagement.entity;

public class BulkOrder implements OrderType{
    @Override
    public Order createOrder() {
        // Return a new instance of Order with specific details for BulkOrder
        Order order = new Order();
        order.setStatus("Bulk");
        // Set additional properties as needed
        return order;
    }
}
