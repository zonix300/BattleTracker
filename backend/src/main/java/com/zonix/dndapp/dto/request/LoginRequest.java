package com.zonix.dndapp.dto.request;


import jakarta.validation.constraints.NotNull;

public record LoginRequest(
        @NotNull String identifier,
        @NotNull String password
) {
}
