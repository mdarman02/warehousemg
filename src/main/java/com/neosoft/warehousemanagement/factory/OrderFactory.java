package com.neosoft.warehousemanagement.factory;

import com.neosoft.warehousemanagement.dto.OrderDto;
import com.neosoft.warehousemanagement.entity.BulkOrder;
import com.neosoft.warehousemanagement.entity.Order;
import com.neosoft.warehousemanagement.entity.OrderType;
import com.neosoft.warehousemanagement.entity.RegularOrder;

import java.math.BigDecimal;

public class OrderFactory {
    public static Order createOrder(OrderDto request) {
        OrderType orderType;

        // Example of differentiating based on the total amount or delivery type
        if (request.getTotalAmount().compareTo(BigDecimal.valueOf(1000)) > 0) {
            orderType = new BulkOrder(); // Create a Bulk Order
//        } else if ("express".equalsIgnoreCase(request.getStatus())) {
//            orderType = new ExpressOrder(); // Create an Express Order
        }
        else {
            orderType = new RegularOrder(); // Create a Regular Order
        }

        // Create and return the order using the respective OrderType
        return orderType.createOrder();
    }
}