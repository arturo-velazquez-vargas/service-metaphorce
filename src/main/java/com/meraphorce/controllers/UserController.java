package com.meraphorce.controllers;

import java.util.List;
import java.util.stream.Stream;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.meraphorce.dtos.UserRequest;
import com.meraphorce.dtos.UserResponse;
import com.meraphorce.models.User;
import com.meraphorce.services.UserService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.extern.slf4j.Slf4j;

/**
 * REST controller for managing users.
 */
@RestController
@RequestMapping("/api/v1/users")
@Slf4j
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;
    
    /**
     * Creates a new user.
     *
     * @param userRequest
     * @return
     */
    @PostMapping
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserRequest userRequest) {
        log.info("Creating user: {}", userRequest);
        User user = modelMapper.map(userRequest, User.class);
        user = userService.createUser( user );
        UserResponse createdUser = modelMapper.map(user, UserResponse.class);
        return ResponseEntity.ok(createdUser);
    }


    /**
     * Fetches all users.
     *
     * @return List of users.
     */
    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        log.info("Fetching all users ");
        Stream<UserResponse> stream = userService.getAllUsers()
        		.stream()
        		.map(user -> UserResponse
	                .builder()
		                .id(user.getId())
		                .name(user.getName())
		                .email(user.getEmail())
		                .build());
        List<UserResponse> users = stream.toList();
        log.info("Fetched {} users", users.size());
        return ResponseEntity.ok(users);
    }

    /**
     * Fetches a user by ID.
     *
     * @param id
     * @return User with the given ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@NotBlank @PathVariable String id) {
        log.info("Fetching user with id: {}", id);
        return userService.getUserById(id)
                .map(user -> {
                    log.info("User found: {}", user);
                    UserResponse dto = modelMapper.map(user, UserResponse.class);
                    return ResponseEntity.ok( dto );
                })
                .orElseGet(() -> {
                    log.warn("User not found with id: {}", id);
                    return ResponseEntity.notFound().build();
                });
    }

    /**
     * Updates a user.
     *
     * @param id
     * @param userRequest
     * @return Updated user.
     */
    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@NotBlank @PathVariable String id, @Valid @RequestBody UserRequest userRequest) {
        log.info("Updating user with id: {}", id);
        User user = modelMapper.map(userRequest, User.class);
        user = userService.updateUser(id, user);
        UserResponse updatedUser = modelMapper.map(user, UserResponse.class);
        return ResponseEntity.ok(updatedUser);
    }

    /**
     * Deletes a user.
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@NotBlank @PathVariable String id) {
        log.info("Deleting user with id: {}", id);
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
    
}
