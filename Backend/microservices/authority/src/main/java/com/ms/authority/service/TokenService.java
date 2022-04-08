package com.ms.authority.service;

import com.ms.authority.entity.Token;
import com.ms.authority.repository.TokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class TokenService {

    private final TokenRepository verificationTokenRepository;

    public void saveVerificationToken (Token token) {
        verificationTokenRepository.save(token);
    }

    public Optional<Token> getToken (String token) {
        return verificationTokenRepository.findByToken(token);
    }
}
