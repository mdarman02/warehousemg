package com.neosoft.warehousemanagement.service;

import com.neosoft.warehousemanagement.dto.LoginDto;
import com.neosoft.warehousemanagement.dto.UserDto;
import com.neosoft.warehousemanagement.entity.UserEntity;

public interface UserService {
    UserEntity addUser(UserDto userDto);

    String login(LoginDto requestDto);
}
