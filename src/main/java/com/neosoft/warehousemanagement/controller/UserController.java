package com.neosoft.warehousemanagement.controller;

import com.neosoft.warehousemanagement.dto.LoginDto;
import com.neosoft.warehousemanagement.dto.TokenResponseDto;
import com.neosoft.warehousemanagement.dto.UserDto;
import com.neosoft.warehousemanagement.entity.UserEntity;
import com.neosoft.warehousemanagement.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@Tag(name="User APIs")
@CrossOrigin(origins = "http://localhost:4200") // Enable CORS for this controller
public class UserController {


    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

//    @PostMapping("/register")
//    public ResponseEntity<String> addUser(@Valid @RequestBody UserDto userDto){
//
//        UserEntity user=userService.addUser(userDto);
//        if(user!=null) {
//            return new ResponseEntity<>("Registration is successfully", HttpStatus.OK);
//        }
//        return new ResponseEntity<>("something went error",HttpStatus.INTERNAL_SERVER_ERROR);
//    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> addUser(@Valid @RequestBody UserDto userDto) {
        logger.info("Creating new user: {}",userDto);
        UserEntity user = userService.addUser(userDto);
        Map<String, String> response = new HashMap<>();
        if (user != null) {
            logger.warn("creating new user failed: {}",userDto);
            response.put("message", "Registration is successful");
            return ResponseEntity.ok(response);
        }
        response.put("message", "Something went wrong");
        logger.info("creating new user successfully: {}",userDto);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@Valid @RequestBody LoginDto loginDto){
        logger.info("user login: {}",loginDto);
        String token =userService.login(loginDto);
        if(token!=null){
            logger.info("login successfully: {}",loginDto);
            TokenResponseDto tokenResponseDto=new TokenResponseDto();
            tokenResponseDto.setToken(token);
            return new ResponseEntity<>(tokenResponseDto,HttpStatus.OK);
        }
        logger.warn("Token is null: {}",loginDto);
        return new ResponseEntity<>("ID/Password is wrong",HttpStatus.UNAUTHORIZED);
    }


}
