package com.meraphorce.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.meraphorce.error.exceptions.UserNotFoundException;
import com.meraphorce.models.User;
import com.meraphorce.respositories.UserRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * Service class for managing users.
 */
@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Creates a new user.
     *
     * @param user the user entity to be created
     * @return the created user entity
     */
    public User createUser(User user) {
        log.debug("Creating user with details: {}", user);
        user.setId(UUID.randomUUID().toString());
        User savedUser = userRepository.save(user);
        log.info("User created with ID: {}", savedUser.getId());
        return savedUser;
    }

    /**
     * Retrieves all users.
     *
     * @return a list of all users
     */
    public List<User> getAllUsers() {
        log.debug("Retrieving all users");
        List<User> users = userRepository.findAll();
        log.info("Retrieved {} users", users.size());
        return users;
    }

    /**
     * Updates an existing user.
     *
     * @param userId the ID of the user to be updated
     * @param user the updated user entity
     * @return the updated user entity
     * @throws UserNotFoundException if the user is not found
     */
    @Transactional
    public User updateUser(String userId,  User user) {
        log.debug("Updating user with ID: {}", userId);
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

        existingUser.setEmail(user.getEmail());
        existingUser.setName(user.getName());
        
        User updatedUser = userRepository.save(existingUser);
        log.info("User with ID: {} updated", updatedUser.getId());
        return updatedUser;
    }

    /**
     * Deletes a user by ID.
     *
     * @param userId the ID of the user to be deleted
     * @throws UserNotFoundException if the user is not found
     */
    @Transactional
    public void deleteUser(String userId) {
        log.debug("Deleting user with ID: {}", userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));
        userRepository.delete(user);
        log.info("User with ID: {} deleted", userId);
    }

    /**
     * Retrieves a user by ID.
     *
     * @param userId the ID of the user to be retrieved
     * @return an optional containing the user if found, or empty if not found
     */
    public Optional<User> getUserById(String userId) {
        log.debug("Retrieving user with ID: {}", userId);
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            log.info("User with ID: {} found", userId);
        } 
        else {
            log.warn("User with ID: {} not found", userId);
        }
        return user;
    }
}
