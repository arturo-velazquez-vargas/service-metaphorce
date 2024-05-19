package com.meraphorce.controllers;

import com.meraphorce.models.User;
import com.meraphorce.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user){
        return ResponseEntity.ok(userService.createUser(user));
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Integer id){
    	User user = userService.getUserById( id );
        return ResponseEntity.ok( user );
    }
    
    @PutMapping
    public ResponseEntity<User> updateUser(@RequestBody User user){
    	user = userService.updateUser( user );
        return ResponseEntity.ok( user );
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id){
    	userService.deleteUser( id );
        return ResponseEntity.ok().build();
    }
    
    @GetMapping("/getUsersNames")
    public ResponseEntity<List<String>> getUsersNames(){
    	return ResponseEntity.ok(userService.getUsersNames());
    }
    
    @PostMapping("/addByBulk")
    public ResponseEntity<List<User>> createByBulk(@RequestBody List<User> usersList){
    	userService.createUsersByBulk( usersList );
        return ResponseEntity.ok( usersList );
    }
    
}
