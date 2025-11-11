package com.zonix.dndapp.service;

import com.zonix.dndapp.dto.entity.AuthContext;
import com.zonix.dndapp.dto.request.LoginRequest;
import com.zonix.dndapp.dto.request.RegisterRequest;
import com.zonix.dndapp.entity.Role;
import com.zonix.dndapp.entity.User;
import com.zonix.dndapp.exception.UserNotFoundException;
import com.zonix.dndapp.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean save(User user) {
        if(userRepository.findByEmail(user.getEmail()).isEmpty()) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRole(Role.USER);
            userRepository.save(user);
            return true;
        }
        return false;

    }

    public boolean userExists(String username) {
        return userRepository.findByEmail(username).isPresent();
    }

    public User createUser(RegisterRequest request) {
        String password = passwordEncoder.encode(request.password());
        User user = new User(request.username(), request.email(), password);
        return userRepository.save(user);

    }

    public boolean checkCredentials(LoginRequest request) {
        Optional<User> userOptional = userRepository.findByEmailOrUsername(request.identifier());

        if (userOptional.isEmpty()) {
            System.out.println("User not found");
            return false;
        }

        User user = userOptional.get();
        return passwordEncoder.matches(request.password(), user.getPassword());
    }

    public User findUser(LoginRequest request) {
        Optional<User> userOptional = userRepository.findByEmailOrUsername(request.identifier());
        return userOptional.orElse(null);
    }

    public User findUser(AuthContext authContext) {
        Optional<User> userOptional = switch (authContext.getRole()) {
            case USER -> findUserByEmail(authContext.getIdentifier());
            case GUEST -> findGuestBySessionToken(authContext.getIdentifier());
            case null, default -> throw new UserNotFoundException("Not valid role");
        };

        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("User was not found");
        }

        return userOptional.get();
    }

    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User createGuest(String sessionToken) {
        User guest = new User(hashSessionToken(sessionToken));
        userRepository.save(guest);
        return guest;
    }

    public Optional<User> findGuestBySessionToken(String sessionToken) {
        return userRepository.findBySessionTokenHash(hashSessionToken(sessionToken));
    }

    private String hashSessionToken(String sessionToken) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(sessionToken.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm is not available");
        }
    }

    public boolean isValidGuestSession(User user, String sessionToken) {
        if (user.getRole() != Role.GUEST || user.getSessionTokenHash() == null) {
            return false;
        }
        String providedHash = hashSessionToken(sessionToken);
        return user.getSessionTokenHash().equals(providedHash) && user.getExpiresAt().isAfter(LocalDateTime.now());
    }

}
