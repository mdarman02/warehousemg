package com.neosoft.warehousemanagement.entity;

public class RegularOrder implements OrderType{
    @Override
    public Order createOrder() {
        // Return a new instance of Order with specific details for RegularOrder
        Order order = new Order();
        order.setStatus("Regular");
        // Set additional properties as needed
        return order;
    }
}
