package com.revature.controllers;

import com.revature.models.DTO.IncomingReimbursementDTO;
import com.revature.models.Reimbursement;
import com.revature.models.User;
import com.revature.services.ReimbursementService;
import com.revature.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/reimbursements")
@CrossOrigin
public class ReimbursementController {

    private final ReimbursementService reimbursementService;
    private final UserService userService;

    @Autowired
    public ReimbursementController(ReimbursementService reimbursementService, UserService userService) {
        this.reimbursementService = reimbursementService;
        this.userService = userService;
    }

    // Create a new reimbursement
    @PostMapping
    public ResponseEntity<Object> createReimbursement(@RequestBody IncomingReimbursementDTO reimbursementDTO) {
        try {
            User user = userService.getUserById(reimbursementDTO.getUserId());
            if (user == null) {
                return ResponseEntity.status(404).body("User not found"); // Return 404 Not Found if user is not found
            }

            Reimbursement newReimbursement = new Reimbursement();
            newReimbursement.setDescription(reimbursementDTO.getDescription());
            newReimbursement.setAmount(reimbursementDTO.getAmount());
            newReimbursement.setStatus(reimbursementDTO.getStatus());
            newReimbursement.setUser(user); // Set the user

            Reimbursement reimbursement = reimbursementService.createReimbursement(newReimbursement);
            return ResponseEntity.status(201).body(reimbursement); // Return 201 Created
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage()); // Return 400 Bad Request
        }
    }

    // Get all reimbursements
    @GetMapping
    public ResponseEntity<List<Reimbursement>> getReimbursements(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        if ("employee".equals(user.getRole())) {
            return ResponseEntity.ok(reimbursementService.getReimbursementsByUserId(user.getUserId()));
        } else if ("manager".equals(user.getRole())) {
            return ResponseEntity.ok(reimbursementService.getAllReimbursements());
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
    // Get all reimbursements for the logged-in user
    @GetMapping("/my-reimbursements")
    public ResponseEntity<List<Reimbursement>> getMyReimbursements(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            List<Reimbursement> reimbursements = reimbursementService.getReimbursementsByUserId(user.getUserId());
            return ResponseEntity.ok(reimbursements);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    // Get reimbursements by user ID
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Reimbursement>> getReimbursementsByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(reimbursementService.getReimbursementsByUserId(userId));
    }

    // Get reimbursements by status
    @GetMapping("/status/{status}")
    public ResponseEntity<?> getReimbursementsByStatus(@PathVariable String status, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You must be logged in to view reimbursements.");
        }

        if ("manager".equals(user.getRole())) {
            // Manager can see all reimbursements with the specified status
            List<Reimbursement> reimbursements = reimbursementService.getReimbursementsByStatus(status);
            return ResponseEntity.ok(reimbursements);
        } else {
            // Employees can only see their own reimbursements with the specified status
            List<Reimbursement> reimbursements = reimbursementService.findByUser_UserIdAndStatus(user.getUserId(), status);
            return ResponseEntity.ok(reimbursements);
        }
    }
    @PutMapping("/{userId}")
    public ResponseEntity<Object> updateUser(@PathVariable Long userId, @RequestBody String newUsername) {
        try {
            User user = userService.updateUser(newUsername, userId);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }
    // Get reimbursements by id and status
    @GetMapping("/user/{userId}/status/{status}")
    public ResponseEntity<List<Reimbursement>> getReimbursementsByUserIdAndStatus(
            @PathVariable("userId") Long userId,
            @PathVariable("status") String status) {
        List<Reimbursement> reimbursements = reimbursementService.findByUser_UserIdAndStatus(userId, status);
        if (reimbursements.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(reimbursements);
    }
    //  allow managers to see all reimbursements

    @DeleteMapping("/{userId}")
    public ResponseEntity<Object> deleteUser(@PathVariable Long userId) {
        try {
            userService.deleteUser(userId);
            return ResponseEntity.ok("User with ID: " + userId + " was deleted.");
        } catch (Exception e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

}
