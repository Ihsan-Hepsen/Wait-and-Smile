package com.server.service.impl;

import com.server.controller.api.WaitListController;
import com.server.domain.User;
import com.server.repository.UserRepository;
import com.server.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void newUser(User user) {
        log.info("New user: {}", user.getName());
        userRepository.save(user);
    }

    @Override
    public Optional<User> getUserById(String userId) {
        log.info("Finding user with ID: {}", userId);
        if (userId.isBlank()) {
            log.error("Provided user ID is empty");
            return Optional.empty();
        }
        return userRepository.findById(userId);
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        log.info("Finding user with email: {}", email);
        return Optional.of(userRepository.findUserByEmail(email));
    }

    @Override
    public List<User> getAllUsers() {
        log.info("Getting all users");
        return userRepository.findAll();
    }

    @Override
    public void updateUser(User user) {
        log.info("Updating user");
        var updatedUser = userRepository.findById(user.getId());
        updatedUser.ifPresent(userRepository::save);
    }

    @Override
    public void deleteUser(String userId) {
        log.info("Removing user with ID: {}", userId);
        if (userId.isBlank()) {
            log.error("Provided user ID is empty");
            return;
        }

        var user = userRepository.findById(userId);
        user.ifPresent(userRepository::delete);
        log.info("Removed user with ID: {}", userId);
    }
}
