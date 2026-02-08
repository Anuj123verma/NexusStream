package com.nexusstream.api.service;

import com.nexusstream.api.dto.RegisterRequest;
import com.nexusstream.api.model.SubscriptionTier;
import com.nexusstream.api.model.User;
import com.nexusstream.api.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegistrationService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public void register(RegisterRequest request) {
        if(userRepository.findByEmail(request.email()).isPresent()){
            throw new IllegalStateException("Email already exists");
        }

        User user = new User();
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setTier(SubscriptionTier.FREE);
        userRepository.save(user);
    }
}
