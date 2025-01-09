//package com.neosoft.warehousemanagement.exception;
//
//import com.neosoft.warehousemanagement.dto.ErrorResponse;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//
//@RestControllerAdvice
//public class GlobalExceptionHandler {
//    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
//
//    // Handle generic exceptions (with logging)
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
//        logger.error("Exception occurred: ", ex);  // Log the exception
//        ErrorResponse errorResponse = new ErrorResponse(
//                "Internal server error",
//                ex.getMessage(),
//                HttpStatus.INTERNAL_SERVER_ERROR.value()
//        );
//        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
//    }
//    // Handle custom exceptions (like ProductNotFoundException)
//    @ExceptionHandler(ProductNotFoundException.class)
//    public ResponseEntity<ErrorResponse> handleProductNotFoundException(ProductNotFoundException ex) {
//        ErrorResponse errorResponse = new ErrorResponse(
//                ex.getMessage(),
//                "Product not found with the given details",
//                HttpStatus.NOT_FOUND.value()
//        );
//        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
//    }
//
//
//
//}
