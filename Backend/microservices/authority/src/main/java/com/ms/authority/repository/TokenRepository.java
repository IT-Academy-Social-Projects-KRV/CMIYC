package com.ms.authority.repository;

import com.ms.authority.entity.Token;
import com.ms.authority.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
    Optional<Token> findByToken(UUID token);
    Optional<Token> findByUser(User user);
}
