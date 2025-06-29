package com.zonix.dndapp.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

public record HpUpdateRequest(
        @NotEmpty Set<Long> combatantIds,
        @Min(1) int amount,
        @NotNull HpChangeType type
) {}
