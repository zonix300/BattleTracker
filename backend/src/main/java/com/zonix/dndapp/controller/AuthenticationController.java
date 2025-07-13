package com.zonix.dndapp.controller;

import com.zonix.dndapp.dto.entity.UserSafeInfo;
import com.zonix.dndapp.dto.request.LoginRequest;
import com.zonix.dndapp.dto.request.RegisterRequest;
import com.zonix.dndapp.dto.response.AuthResponse;
import com.zonix.dndapp.entity.User;
import com.zonix.dndapp.service.JwtService;
import com.zonix.dndapp.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
    private final UserService userService;
    private final JwtService jwtService;


    public AuthenticationController(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        if (userService.userExists(request.email())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists");
        }

        User user = userService.createUser(request);

        UserSafeInfo userSafeInfo = new UserSafeInfo(
                user.getId().toString(),
                user.getUsername(),
                user.getEmail(),
                user.getRole().name()
        );

        String token = jwtService.generateToken(request.email());

        AuthResponse response = new AuthResponse(token, userSafeInfo);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        if (userService.checkCredentials(request)) {
            User user = userService.findUser(request);
            UserSafeInfo userSafeInfo = new UserSafeInfo(
                    user.getId().toString(),
                    user.getUsername(),
                    user.getEmail(),
                    user.getRole().name()
            );

            String token = jwtService.generateToken(request.email());

            AuthResponse response = new AuthResponse(token, userSafeInfo);

            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");

    }
}
