package com.hospital.controller;

import com.hospital.dto.LoginRequest;
import com.hospital.entity.User;
import com.hospital.service.AuthService;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthController {

    private final AuthService service;

    public AuthController(AuthService service) {
        this.service = service;
    }

    @PostMapping("/login")
    public User login(
            @RequestBody LoginRequest request) {

        return service.login(
                request.getUsername(),
                request.getPassword()
        );
    }
}