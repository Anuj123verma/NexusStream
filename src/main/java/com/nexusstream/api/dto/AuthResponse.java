package com.nexusstream.api.dto;

public record AuthResponse (
        String token,
        String email,
        String tier
){}

