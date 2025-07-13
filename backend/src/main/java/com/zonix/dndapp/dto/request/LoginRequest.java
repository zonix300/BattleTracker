package com.zonix.dndapp.dto.request;


public record LoginRequest(
        String username,
        String email,
        String password
) {
}
