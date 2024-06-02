package com.meraphorce.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.meraphorce.dtos.RegisterRequest;
import com.meraphorce.dtos.UserResponse;
import com.meraphorce.models.User;
import com.meraphorce.respositories.UserRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * Service class for managing authentication.
 */
@Slf4j
@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passEncoder;
    
	public UserResponse registerUser(RegisterRequest userInfo) {
		log.info( "Registering user." );
		
		final User user = new User();
		user.setEmail( userInfo.getUsername() );
		user.setName( userInfo.getName() );
		user.setPassword( passEncoder.encode(userInfo.getPassword()) );
		user.setRoles(null);
		userRepository.save( user );
		
		UserResponse response = new UserResponse();
		response.setId( user.getId() );
		response.setEmail( user.getEmail() );
		response.setName( user.getName() );
		return response;
	}
}
