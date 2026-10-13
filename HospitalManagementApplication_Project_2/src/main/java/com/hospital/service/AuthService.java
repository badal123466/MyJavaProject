package com.hospital.service;

import com.hospital.entity.User;
import com.hospital.repository.UserRepository;

import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository repository;

    public AuthService(UserRepository repository) {
        this.repository = repository;
    }

    public User login(
            String username,
            String password) {

        User user =
                repository.findByUsername(username)
                        .orElseThrow(() ->
                                new RuntimeException(
                                    "Invalid username"
                                ));

        if (!user.getPassword().equals(password)) {

            throw new RuntimeException(
                    "Invalid password"
            );
        }

        if (!user.isActive()) {

            throw new RuntimeException(
                    "User account is inactive"
            );
        }

        return user;
    }
}