package com.meraphorce.services;

import com.meraphorce.exceptions.UserNotFoundException;
import com.meraphorce.models.User;
import com.meraphorce.respositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User createUser(User user){
        return userRepository.save(user);
    }

    public  List<User> getAllUsers(){
        return userRepository.findAll();
    }
    
    public User getUserById(Integer id){
    	
    	Optional<User> userOpt = userRepository.findById( id );
    	if( userOpt.isEmpty() ) {
    		throw new UserNotFoundException( id );
    	}
    	
        return userOpt.get();
    }
    
    public User updateUser(User user){
    	
    	Optional<User> userOpt = userRepository.findById( user.getId() );
    	if( userOpt.isEmpty() ) {
    		throw new UserNotFoundException( user.getId() );
    	}
    	
    	User dbUser = userOpt.get();
    	dbUser.setEmail( user.getEmail() );
    	dbUser.setName( user.getName() );
    	
        return userRepository.save( dbUser );
    }
    
    public void deleteUser(Integer id){
    	boolean exist = userRepository.existsById( id );
    	if( ! exist ) {
    		throw new UserNotFoundException( id );
    	}
        userRepository.deleteById( id );
    }
    
}
