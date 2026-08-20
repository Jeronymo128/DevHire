package com.devhire.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.devhire.model.User;
import com.devhire.dto.UserRequestDTO;
import com.devhire.dto.UserResponseDTO;
import com.devhire.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(
        UserRepository userRepository,
        PasswordEncoder passwordEncoder) {

      this.userRepository = userRepository;
      this.passwordEncoder = passwordEncoder;
    }


    private UserResponseDTO toResponseDTO(User user) {
    UserResponseDTO response = new UserResponseDTO();

    response.setId(user.getId());
    response.setName(user.getName());
    response.setEmail(user.getEmail());
    response.setRole(user.getRole());

    return response;
    }

    public UserResponseDTO createUser(UserRequestDTO userRequest) {
    if (userRepository.existsByEmail(userRequest.getEmail())) {
        throw new IllegalArgumentException("Email already registered");
    }

    User user = new User();

    user.setName(userRequest.getName());
    user.setEmail(userRequest.getEmail());
    String hashedPassword = passwordEncoder.encode(userRequest.getPassword());
    user.setPassword(hashedPassword);
    user.setRole(userRequest.getRole());

    User savedUser = userRepository.save(user);

    return toResponseDTO(savedUser);
    }
}