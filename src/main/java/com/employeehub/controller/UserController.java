package com.employeehub.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

import com.employeehub.dto.LoginRequest;
import com.employeehub.dto.LoginResponse;
import com.employeehub.dto.RegisterResponse;
import com.employeehub.entity.User;
import com.employeehub.service.UserService;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    // Register User
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(
            @Valid @RequestBody User user) {

        RegisterResponse registeredUser =
                userService.registerUser(user);

        if (registeredUser == null) {

            return ResponseEntity.badRequest()
                    .body("Email already registered");
        }

        return ResponseEntity.status(201)
                .body(registeredUser);
    }

    // Login User
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(
            @RequestBody LoginRequest loginRequest) {

        LoginResponse loginResponse =
                userService.loginUser(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                );

        if (loginResponse == null) {

            return ResponseEntity.status(401)
                    .body("Invalid email or password");
        }

        return ResponseEntity.ok(loginResponse);
    }
}