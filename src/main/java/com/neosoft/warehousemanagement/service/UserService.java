package com.neosoft.warehousemanagement.service;

import com.neosoft.warehousemanagement.dto.LoginDto;
import com.neosoft.warehousemanagement.dto.UserDto;

public interface UserService {
    void addUser(UserDto userDto);

    String login(LoginDto requestDto);
}
