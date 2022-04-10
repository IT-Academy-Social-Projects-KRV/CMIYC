package com.ms.authority.repository;

import java.util.Optional;
import java.util.UUID;

import com.ms.authority.entity.Token;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
    Optional<Token> findByToken(UUID token);
}