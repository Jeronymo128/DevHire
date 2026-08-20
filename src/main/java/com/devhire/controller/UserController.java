package com.devhire.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devhire.dto.UserRequestDTO;
import com.devhire.dto.UserResponseDTO;
import com.devhire.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    
    @PostMapping
    public UserResponseDTO createUser(
        @Valid @RequestBody UserRequestDTO userRequest) {

    return userService.createUser(userRequest);
}
    
}