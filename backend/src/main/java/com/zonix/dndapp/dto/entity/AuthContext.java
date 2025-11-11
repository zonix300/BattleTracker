package com.zonix.dndapp.dto.entity;

import com.zonix.dndapp.entity.Role;
import org.springframework.security.core.userdetails.UserDetails;

public class AuthContext {
    String identifier;
    Role role;

    public AuthContext(String identifier, Role role) {
        this.identifier = identifier;
        this.role = role;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }


    public static AuthContext resolve(UserDetails userDetails, String sessionToken) {
        if (userDetails != null) {
            return new AuthContext(userDetails.getUsername(), Role.USER);
        }

        if (sessionToken != null) {
            return new AuthContext(sessionToken, Role.GUEST);
        }

        throw new RuntimeException("Was unable to resolve the user authentication data");
    }
}
