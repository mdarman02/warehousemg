//package com.neosoft.warehousemanagement.controller;
//
//import com.neosoft.warehousemanagement.enumDto.Category;
//import io.swagger.v3.oas.annotations.tags.Tag;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.Arrays;
//
//@RestController
//@RequestMapping("/api/categories")
//@Tag(name = "Category APIs")
//@CrossOrigin(origins = "http://localhost:4200") // Enable CORS for this controller
//public class CategoryController {
//
//    // Endpoint to return the enum values as a list of strings
//    @GetMapping
//    public String[] getCategories() {
//        return Arrays.stream(Category.values())
//                .map(Enum::name)  // Converts each enum to a string
//                .toArray(String[]::new);  // Collects them into a String array
//    }
//}