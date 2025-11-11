package com.zonix.dndapp.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LobbyCreationRequest (
    @NotBlank String name,
    String password
) {}
