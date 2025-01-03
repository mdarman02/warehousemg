package com.neosoft.warehousemanagement.controller;

import com.neosoft.warehousemanagement.dto.LoginDto;
import com.neosoft.warehousemanagement.dto.TokenResponseDto;
import com.neosoft.warehousemanagement.dto.UserDto;
import com.neosoft.warehousemanagement.entity.UserEntity;
import com.neosoft.warehousemanagement.service.UserService;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/api/auth")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> addUser(@Valid @RequestBody UserDto userDto){

        UserEntity user=userService.addUser(userDto);
        if(user!=null) {
            return new ResponseEntity<>("Registration is successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("something went error",HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@Valid @RequestBody LoginDto loginDto){
        String token =userService.login(loginDto);
        if(token!=null){
            TokenResponseDto tokenResponseDto=new TokenResponseDto();
            tokenResponseDto.setToken(token);
            return new ResponseEntity<>(tokenResponseDto,HttpStatus.OK);
        }
        return new ResponseEntity<>("ID/Password is wrong",HttpStatus.UNAUTHORIZED);
    }


}
