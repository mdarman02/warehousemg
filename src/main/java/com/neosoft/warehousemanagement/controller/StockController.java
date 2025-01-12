package com.neosoft.warehousemanagement.controller;
import com.neosoft.warehousemanagement.dto.StockMovementDto;
import com.neosoft.warehousemanagement.service.StockService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/stock")
@Tag(name="Stock APIs")
@CrossOrigin(origins = "http://localhost:4200") // Enable CORS for this controller
public class StockController {


    private static final Logger logger = LoggerFactory.getLogger(StockController.class);
    private StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    // Endpoint to add stock
    @PostMapping("/add")
    @Operation(summary = "Add stock")
    public ResponseEntity<String> addStock(@RequestBody StockMovementDto stockMovementDto) {
        logger.info("Add stock: {}",stockMovementDto);
        try {
            // Call the service method to add stock
            stockService.addStock(
                    stockMovementDto.getProductId(),
                    stockMovementDto.getQuantity(),
                    stockMovementDto.getReason()
            );
            logger.info("Added stock successfully: {}",stockMovementDto);
            return new ResponseEntity<>("Stock added successfully", HttpStatus.CREATED);

//            return ResponseEntity.ok("Stock added successfully.");
        } catch (Exception e) {
            logger.info("Added stock failed: {}",stockMovementDto);
            return ResponseEntity.status(400).body("Error: " + e.getMessage());
        }
    }


    @PutMapping("/reduceStock")
    @Operation(summary = "Reduce Stock")
    public ResponseEntity<String> reduceStock(@RequestBody StockMovementDto stockMovementDto) {

        logger.info("Reduce stock: {}",stockMovementDto);
        try {
            // Call the service method to add stock
            stockService.reduceStock(
                    stockMovementDto.getProductId(),
                    stockMovementDto.getQuantity(),
                    stockMovementDto.getReason()
            );
            logger.info("Reduce stock successfully: {}",stockMovementDto);
            return new ResponseEntity<>("Stock added successfully", HttpStatus.CREATED);
//            return ResponseEntity.ok("Stock added successfully.");
        } catch (Exception e) {
            logger.warn("Reduce stock failed: {}",stockMovementDto);
            return ResponseEntity.status(400).body("Error: " + e.getMessage());
        }
    }

    @DeleteMapping("/remove")
    @Operation(summary = "Remove stock")
    public ResponseEntity<String> removeStock(@RequestBody StockMovementDto stockMovementDto) {
        logger.info("Remove stock: {}",stockMovementDto);
        try {
            // Call the service method to add stock
            stockService.removeStock(
                    stockMovementDto.getProductId(),
                    stockMovementDto.getQuantity(),
                    stockMovementDto.getReason()
            );
            logger.info("Remove stock successfully: {}",stockMovementDto);
            return ResponseEntity.ok("Stock added successfully.");
        } catch (Exception e) {
            logger.warn("Remove stock failed: {}",stockMovementDto);
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
