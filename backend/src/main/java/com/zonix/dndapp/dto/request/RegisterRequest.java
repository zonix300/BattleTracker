package com.zonix.dndapp.dto.request;

public record RegisterRequest(
        String username,
        String email,
        String password
) {
}
