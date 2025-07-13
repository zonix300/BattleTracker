package com.zonix.dndapp.service;

import com.zonix.dndapp.dto.request.LoginRequest;
import com.zonix.dndapp.dto.request.RegisterRequest;
import com.zonix.dndapp.entity.Role;
import com.zonix.dndapp.entity.User;
import com.zonix.dndapp.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
        User user = new User();
        user.setUsername(request.username());
        user.setEmail(request.email());
        user.setPassword(password);
        user.setRole(Role.USER);
        return userRepository.save(user);

    }

    public boolean checkCredentials(LoginRequest request) {
        Optional<User> userOptional = userRepository.findByEmail(request.email());

        if (userOptional.isEmpty()) {
            System.out.println("User not found");
            return false;
        }

        User user = userOptional.get();
        return passwordEncoder.matches(request.password(), user.getPassword());
    }

    public User findUser(LoginRequest request) {
        Optional<User> userOptional = userRepository.findByEmail(request.email());
        return userOptional.orElse(null);
    }

}
