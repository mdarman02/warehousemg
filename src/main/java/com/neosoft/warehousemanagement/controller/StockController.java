package com.neosoft.warehousemanagement.controller;
import com.neosoft.warehousemanagement.dto.StockMovementDto;
import com.neosoft.warehousemanagement.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/stock")
public class StockController {

    private StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    // Endpoint to add stock
    @PostMapping("/add")
    public ResponseEntity<String> addStock(@RequestBody StockMovementDto stockMovementDto) {
        try {
            // Call the service method to add stock
            stockService.addStock(
                    stockMovementDto.getProductId(),
                    stockMovementDto.getQuantity(),
                    stockMovementDto.getReason()
            );
            return ResponseEntity.ok("Stock added successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Error: " + e.getMessage());
        }
    }

    @DeleteMapping("/remove")
    public ResponseEntity<String> removeStock(@RequestBody StockMovementDto stockMovementDto) {
        try {
            // Call the service method to add stock
            stockService.removeStock(
                    stockMovementDto.getProductId(),
                    stockMovementDto.getQuantity(),
                    stockMovementDto.getReason()
            );
            return ResponseEntity.ok("Stock added successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Error: " + e.getMessage());
        }
    }
//    @PostMapping("/update")
//    public ResponseEntity<String> updateStock(@RequestBody StockMovementDto stockMovementDto) {
//        try {
//            // Call the service method to add stock
//            stockService.removeStock(
//                    stockMovementDto.getProductId(),
//                    stockMovementDto.getQuantity(),
//                    stockMovementDto.getReason()
//
//            );
//            return ResponseEntity.ok("Stock added successfully.");
//        } catch (Exception e) {
//            return ResponseEntity.status(400).body("Error: " + e.getMessage());
//        }
//    }
}
