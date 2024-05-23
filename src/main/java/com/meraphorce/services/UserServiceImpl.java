package com.meraphorce.services;

import com.meraphorce.models.User;
import com.meraphorce.respositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    public User createUser(User user){
        return userRepository.save(user);
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User getUserById(String id){
        return userRepository.findById(id).orElse(null);
    }

    public User updateUser(String id, User user){
        Optional<User> optionalExistingUser = userRepository.findById(id);

        if(optionalExistingUser.isPresent()){
            User users = optionalExistingUser.get();
            users.setName(user.getName());
            users.setEmail(user.getEmail());

            return userRepository.save(users);
        } else {
            return null;
        }
    }

    public void deleteUser(String id){
        userRepository.deleteById(id);
    }
}
