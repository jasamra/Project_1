package com.revature.controllers;

import com.revature.models.User;
import com.revature.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@CrossOrigin
public class UserController {

    private UserService userService;


    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

//    // Create a new user
//    @PostMapping
//    public ResponseEntity<Object> registerUser(@RequestBody User newUser) {
//        try {
//            User user = userService.registerUser(newUser);
//            return ResponseEntity.status(201).body(user); // Return 201 Created
//        } catch (Exception e) {
//            return ResponseEntity.status(400).body(e.getMessage()); // Return 400 Bad Request
//        }
//    }

    // Register a new user
    @PostMapping("/register")
    public ResponseEntity<Object> registerUser(@RequestBody User newUser) {
        try {
            User user = userService.registerUser(newUser);
            return ResponseEntity.status(201).body(user); // Return 201 Created
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage()); // Return 400 Bad Request
        }
    }
    // Get all users
    @GetMapping
    public ResponseEntity<?> getAllUsers(HttpSession session) {
        System.out.println("Session Role: " + session.getAttribute("role")); // Log to check what's in the session
        String role = (String) session.getAttribute("role");
        if (!"manager".equals(role)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access Denied");
        }
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }


    // Get a user by username
    @GetMapping("/{username}")
    public ResponseEntity<Object> getUserByUsername(@PathVariable String username) {
        try {
            User user = userService.getUserByUsername(username);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.status(404).body(e.getMessage()); // Return 404 Not Found
        }
    }
    // update user
    @PutMapping("/{userId}")
    public ResponseEntity<Object> updateUser(@PathVariable Long userId, @RequestBody String newUsername) {
        try {
            User user = userService.updateUser(newUsername, userId);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }
    // Delete user and their reimbursements, manager role only
    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId, HttpSession session) {
        String userRole = (String) session.getAttribute("role");

        // Check if the user is a manager
        if (!"manager".equals(userRole)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access Denied");
        }

        try {
            // This method needs to be sure that deleting a user also cascades to delete their reimbursements
            userService.deleteUserAndReimbursements(userId);
            return ResponseEntity.ok("User and their reimbursements with ID: " + userId + " were deleted.");
        } catch (Exception e) {
            return ResponseEntity.status(404).body("User not found: " + e.getMessage());
        }
    }


}
