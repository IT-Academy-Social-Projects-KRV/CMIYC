package com.ms.authority.repository;

import com.ms.authority.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);

    Optional<User> findById(Integer id);

    boolean existsByEmail(String email);

    @Query(value = "FROM User u WHERE (:email IS NOT NULL AND u.email LIKE %:email%) " +
            "AND (:firstName IS NOT NULL AND u.firstName LIKE %:firstName%) " +
            "AND (:lastName IS NOT NULL AND u.lastName LIKE %:lastName%) " +
            "AND (:isActive IS NOT NULL AND u.isActive =:isActive)",
            countQuery = "SELECT count(*) FROM User u " +
                    "WHERE (:email IS NOT NULL AND u.email LIKE %:email%) " +
                    "AND (:firstName IS NOT NULL AND u.firstName LIKE %:firstName%) " +
                    "AND (:lastName IS NOT NULL AND u.lastName LIKE %:lastName%) " +
                    "AND (:isActive IS NOT NULL AND u.isActive =:isActive)")
    Page<User> findUserByParams(@Param(value = "email") String email,
                                @Param(value = "firstName") String firstName,
                                @Param(value = "lastName") String lastName,
                                @Param(value = "isActive") Boolean isActive, Pageable pageable);
}
