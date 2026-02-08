package com.nexusstream.api.service;

import com.nexusstream.api.dto.AuthResponse;
import com.nexusstream.api.dto.LoginRequest;
import com.nexusstream.api.model.User;
import com.nexusstream.api.repository.UserRepository;
import com.nexusstream.api.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthResponse login(LoginRequest request) {
        // 1. Check if user exists
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        // 2. Verify password
        // LLD: matches(rawPassword, encodedPassword)
        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        // 3. Generate the "Passport" (JWT)
        String token = jwtService.generateToken(user.getEmail(), user.getTier().name());

        // 4. Return the response DTO
        return new AuthResponse(token, user.getEmail(), user.getTier().name());
    }
}