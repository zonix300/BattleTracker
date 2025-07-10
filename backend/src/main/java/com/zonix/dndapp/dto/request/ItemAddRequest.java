package com.zonix.dndapp.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record ItemAddRequest(
        @NotNull(message = "Template id must be provided")
        Long templateId,
        @Min(value = 1) int amount
) {
}
