package com.employeehub.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.employeehub.entity.User;
import com.employeehub.repository.UserRepository;
import com.employeehub.dto.LoginResponse;
import com.employeehub.dto.RegisterResponse;
import com.employeehub.security.JwtUtil;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public RegisterResponse registerUser(User user) {

        if (userRepository.existsByEmail(user.getEmail())) {

            return null;
        }

        user.setPassword(
                passwordEncoder.encode(user.getPassword())
        );

        User savedUser = userRepository.save(user);

        return new RegisterResponse(
                savedUser.getId(),
                savedUser.getName(),
                savedUser.getEmail()
        );
    }

    public LoginResponse loginUser(
            String email,
            String password) {

        User user = userRepository.findByEmail(email);

        if (user != null
                && passwordEncoder.matches(
                        password,
                        user.getPassword())) {

            String token = jwtUtil.generateToken(
                    user.getEmail()
            );

            return new LoginResponse(
                    user.getId(),
                    user.getName(),
                    user.getEmail(),
                    token
            );
        }

        return null;
    }
}