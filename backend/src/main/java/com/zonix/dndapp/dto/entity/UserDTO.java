package com.zonix.dndapp.dto.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zonix.dndapp.entity.Role;

import java.time.LocalDateTime;

public record UserDTO(
        String username,
        String email,
        String role,
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime expiresAt) {

}
