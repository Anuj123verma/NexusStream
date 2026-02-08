package com.nexusstream.api.controller;

import com.nexusstream.api.dto.AuthResponse;
import com.nexusstream.api.dto.LoginRequest;
import com.nexusstream.api.dto.RegisterRequest;
import com.nexusstream.api.service.AuthService;
import com.nexusstream.api.service.RegistrationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor // LLD: Automates constructor injection for final fields
public class AuthController {

    private final RegistrationService registrationService;
    private final AuthService authService; // Inject the new service

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequest request) {
        // We delegate the actual work to the Service layer
        registrationService.register(request);

        // HLD: Always return the correct HTTP Status Code (201 Created)
        return new ResponseEntity<>("User registered successfully", HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }
}