package com.devhire.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.devhire.model.User;
import com.devhire.dto.LoginRequestDTO;
import com.devhire.dto.UserRequestDTO;
import com.devhire.dto.UserResponseDTO;
import com.devhire.repository.UserRepository;
import com.devhire.exception.InvalidCredentialsException;

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

    public UserResponseDTO login(LoginRequestDTO loginRequest) {

    User user = userRepository.findByEmail(loginRequest.getEmail())
            .orElseThrow(() -> new InvalidCredentialsException("Invalid email or password"));

    if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
        throw new InvalidCredentialsException("Invalid email or password");
    }

    return toResponseDTO(user);
}
}