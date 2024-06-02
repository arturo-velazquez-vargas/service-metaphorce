package com.meraphorce.respositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.meraphorce.models.User;

/**
 * Repository for managing User entities.
 */
@Repository
public interface UserRepository extends JpaRepository<User, String>{

    public Optional<User> findByEmail(String name);
    
}
