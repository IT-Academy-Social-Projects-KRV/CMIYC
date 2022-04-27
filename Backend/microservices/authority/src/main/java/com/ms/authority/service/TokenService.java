package com.ms.authority.service;

import com.ms.authority.entity.Token;
import com.ms.authority.entity.User;
import com.ms.authority.repository.TokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

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

    public void deleteTokenByUser(User user){
        Optional<Token> token = verificationTokenRepository.findByUser(user);
        token.ifPresent(verificationTokenRepository::delete);
    }
}
