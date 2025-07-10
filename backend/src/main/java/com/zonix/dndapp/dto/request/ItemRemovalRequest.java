package com.zonix.dndapp.dto.request;

import jakarta.validation.constraints.NotEmpty;

import java.util.Set;

public record ItemRemovalRequest(
        @NotEmpty(message = "At least one ID must be provided")
        Set<Long> itemIds
) {
}
