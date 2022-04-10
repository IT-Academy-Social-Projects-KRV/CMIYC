package com.ms.authority.service;

import java.util.Optional;
import java.util.UUID;

import com.ms.authority.entity.Token;
import com.ms.authority.repository.TokenRepository;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TokenService {

    private final TokenRepository verificationTokenRepository;

    public void saveVerificationToken (Token token) {
        verificationTokenRepository.save(token);
    }

    public Optional<Token> getToken (String token) {
        return verificationTokenRepository.findByToken(UUID.fromString(token));
    }
}