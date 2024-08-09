package com.revature.services;

import com.revature.DAOs.UserDAO;
import com.revature.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private UserDAO userDAO;

    @Autowired
    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public User registerUser(User newUser) {
        if (newUser.getUsername() == null || newUser.getPassword() == null) {
            throw new IllegalArgumentException("Username and password cannot be null.");
        }

        if (userDAO.findByUsername(newUser.getUsername()) != null) {
            throw new IllegalArgumentException("Username already exists.");
        }

        if (newUser.getRole() == null) {
            newUser.setRole("employee");
        }

        return userDAO.save(newUser);
    }

    public List<User> getAllUsers() {
        return userDAO.findAll();
    }

    public User getUserByUsername(String username) {
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty.");
        }

        User user = userDAO.findByUsername(username);
        if (user == null) {
            throw new RuntimeException("User not found.");
        }

        return user;
    }

    public User updateUser(String newUsername, Long userId) {
        if (newUsername == null || newUsername.isEmpty()) {
            throw new IllegalArgumentException("New username cannot be null or empty.");
        }

        Optional<User> existingUser = userDAO.findById(userId);
        if (existingUser.isPresent()) {
            User user = existingUser.get();
            user.setUsername(newUsername);
            return userDAO.save(user);
        } else {
            throw new RuntimeException("User not found.");
        }
    }

    public void deleteUser(Long userId) {
        if (!userDAO.existsById(userId)) {
            throw new RuntimeException("User not found.");
        }

        userDAO.deleteById(userId);
    }

    // Add this method
    public User getUserById(Long userId) {
        return userDAO.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
    }
}
