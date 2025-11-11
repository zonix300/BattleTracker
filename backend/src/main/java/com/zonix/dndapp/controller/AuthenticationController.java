package com.zonix.dndapp.controller;

import com.zonix.dndapp.dto.entity.UserDTO;
import com.zonix.dndapp.dto.request.LoginRequest;
import com.zonix.dndapp.dto.request.RegisterRequest;
import com.zonix.dndapp.dto.response.AuthResponse;
import com.zonix.dndapp.entity.User;
import com.zonix.dndapp.service.JwtService;
import com.zonix.dndapp.service.UserService;
import jakarta.validation.Valid;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
    private final UserService userService;
    private final JwtService jwtService;


    public AuthenticationController(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @PostMapping("/guest")
    public ResponseEntity<?> initializeGuestSession() {
        String sessionToken = UUID.randomUUID().toString();

        User guest = userService.createGuest(sessionToken);

        UserDTO userDTO = new UserDTO(
                "guest_username",
                "guest_email",
                guest.getRole().name(),
                guest.getExpiresAt()
        );

        return ResponseEntity.ok(new AuthResponse(sessionToken, userDTO));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        if (userService.userExists(request.email())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists");
        }

        User user = userService.createUser(request);

        UserDTO userDTO = new UserDTO(
                user.getUsername(),
                user.getEmail(),
                user.getRole().name(),
                user.getExpiresAt()
        );

        String token = jwtService.generateToken(request.email());

        AuthResponse response = new AuthResponse(token, userDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }   

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequest request) {
        if (userService.checkCredentials(request)) {
            User user = userService.findUser(request);
            String token = jwtService.generateToken(user.getEmail());
            Date expiresAt = jwtService.extractExpiration(token);

            user.setExpiresAt(expiresAt.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime());

            UserDTO userSafeInfo = new UserDTO(
                    user.getUsername(),
                    user.getEmail(),
                    user.getRole().name(),
                    user.getExpiresAt()
            );

            AuthResponse response = new AuthResponse(token, userSafeInfo);

            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
    }
}
