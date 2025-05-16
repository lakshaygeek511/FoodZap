package com.foodzap.repositories;

import com.foodzap.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Find a user by email
    Optional<User> findByEmail(String email);
    
    // Find a user by name
    Optional<User> findByName(String name);
    
    // Check if a user exists with the given email
    boolean existsByEmail(String email);
} 