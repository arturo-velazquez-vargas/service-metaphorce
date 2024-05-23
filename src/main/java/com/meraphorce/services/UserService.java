package com.meraphorce.services;

import com.meraphorce.models.User;
import java.util.List;

public interface UserService {

    User createUser(User user);

    List<User> getAllUsers();

    User getUserById(String id);

    void deleteUser(String id);

    User updateUser(String id, User user);

}

