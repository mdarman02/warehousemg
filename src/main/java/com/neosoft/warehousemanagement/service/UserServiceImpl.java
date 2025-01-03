package com.neosoft.warehousemanagement.service;

import com.neosoft.warehousemanagement.dto.LoginDto;
import com.neosoft.warehousemanagement.dto.UserDto;
import com.neosoft.warehousemanagement.entity.UserEntity;
import com.neosoft.warehousemanagement.jwt.JWTService;
import com.neosoft.warehousemanagement.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    private JWTService jwtService;
    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository,JWTService jwtService) {
        this.userRepository = userRepository;
        this.jwtService=jwtService;
    }





    @Override
    public UserEntity addUser(UserDto userDto) {
        UserEntity user=new UserEntity();
        user.setId(userDto.getId());
        user.setFirstname(userDto.getFirstname());
        user.setLastname(userDto.getLastname());
        user.setEmail(userDto.getEmail());

        user.setPassword(BCrypt.hashpw(userDto.getPassword(),BCrypt.gensalt(10)));
        UserEntity savedUser=userRepository.save(user);
        return savedUser;

    }

    @Override
    public  String login(LoginDto loginDto) {
        Optional<UserEntity> opUser = userRepository.findByEmail(loginDto.getEmail());
        if (opUser.isPresent()) {
            UserEntity user=opUser.get();
            if(BCrypt.checkpw(loginDto.getPassword(),user.getPassword()))
            return jwtService.generateToken(user);
        }
        return null;
    }
    }
