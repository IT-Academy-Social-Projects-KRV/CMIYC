package com.ms.authority.repository;

import com.ms.authority.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
    Optional<User> findById(Integer id);


    @Transactional
    @Modifying
    @Query("UPDATE User a " +
            "SET a.isActive = TRUE WHERE a.email = ?1")
    int enableUser(String email);
}
