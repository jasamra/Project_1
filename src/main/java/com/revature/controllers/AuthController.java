package com.revature.controllers;

import com.revature.models.DTO.LoginDTO;
import com.revature.models.DTO.OutgoingUserDTO;
import com.revature.models.User;
import com.revature.services.AuthService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
//set crossorigin to allow origin from localhost:3000, and allow credentials
@CrossOrigin(origins="http://localhost:3000", allowCredentials = "true")
public class AuthController {


    //Autowire AuthService
    private AuthService as;

    @Autowired
    public AuthController(AuthService as) {
        this.as = as;
    }

    //NOTE: our HTTP Session is coming in via parameters this time (to be sent to the Service)
    @PostMapping
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO, HttpSession session) {
        User user = as.validateUser(loginDTO.getUsername(), loginDTO.getPassword());
        if (user != null) {
            session.setAttribute("user", user);  // Store user in session
            session.setAttribute("role", user.getRole());
            System.out.println("Logged in user role: " + session.getAttribute("role"));  // Log to confirm role
            OutgoingUserDTO outUser = new OutgoingUserDTO(Math.toIntExact(user.getUserId()), user.getUsername(), user.getRole());
            return ResponseEntity.ok(outUser);
        } else {
            return ResponseEntity.status(401).body("Invalid Credentials! Try again");
        }
    }
}