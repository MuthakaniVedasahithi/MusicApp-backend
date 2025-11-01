package com.example.InitialOne.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.InitialOne.model.Admin;
import com.example.InitialOne.model.User;
import com.example.InitialOne.service.AuthService;


@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {
	 @Autowired
	    private AuthService authService;

	    // User endpoints
	    @PostMapping("/user/signup")
	    public ResponseEntity<?> userSignup(@RequestBody User user) {
	        return ResponseEntity.ok(authService.registerUser(user));
	    }

	    @PostMapping("/user/login")
	    public ResponseEntity<?> userLogin(@RequestBody User user) {
	        Optional<User> loggedInUser = authService.loginUser(user.getEmail(), user.getPassword());
	        if(loggedInUser.isPresent()) return ResponseEntity.ok(loggedInUser.get());
	        return ResponseEntity.status(401).body("Invalid user credentials");
	    }

	    // Admin endpoints
	    @PostMapping("/admin/signup")
	    public ResponseEntity<?> adminSignup(@RequestBody Admin admin) {
	        return ResponseEntity.ok(authService.registerAdmin(admin));
	    }

	    @PostMapping("/admin/login")
	    public ResponseEntity<?> adminLogin(@RequestBody Admin admin) {
	        Optional<Admin> loggedInAdmin = authService.loginAdmin(admin.getEmail(), admin.getPassword());
	        if(loggedInAdmin.isPresent()) return ResponseEntity.ok(loggedInAdmin.get());
	        return ResponseEntity.status(401).body("Invalid admin credentials");
	    }
	}
