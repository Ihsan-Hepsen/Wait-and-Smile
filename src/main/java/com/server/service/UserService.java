package com.server.service;

import com.server.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    void newUser(User user);
    Optional<User> getUserById(String userId);
    List<User> getAllUsers();
    void deleteUser(String userId);

}
