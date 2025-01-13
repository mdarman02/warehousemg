package com.neosoft.warehousemanagement.dto;


import com.neosoft.warehousemanagement.enumDto.Category;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

    private Long id;
    private String name;
    private String sku;
    private Category category;
    private BigDecimal price;
    private String description;


    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
//     Getter method: Return the enum type
//public Category getCategory() {
//    return category;
//}
//
//    // Setter method: Accept the enum type
//    public void setCategory(String category) {
//        if (category != null) {
//            this.category = Category.valueOf(category);  // Converts String to enum
//        }
//    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
