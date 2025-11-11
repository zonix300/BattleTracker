package com.zonix.dndapp.dto.request;

import jakarta.validation.constraints.*;

public record PlayerCharacterSearchRequest(
        @NotBlank(message = "Name cannot be blank")
        @Size(min = 1, max = 50, message = "Name must be between 1 and 50 characters long")
        @Pattern(regexp = "^[a-zA-Z0-9\\s\\-']+$", message = "Name contains invalid characters")
        String name,

        @NotBlank(message = "Sort Direction cannot be blank")
        String sortDirection,

        @Min(value = 0, message = "Page must be non-negative")
        @Max(value = 1000, message = "Page number is too big")
        int page,

        @Min(value = 1, message = "Size must be at least 1")
        @Max(value = 100, message = "Size cannot exceed 100")
        int size,

        @Pattern(regexp = "^(name|id|armorClass|dexterity)$", message = "Invalid sort field")
        String sortBy
) {
    @Override
    public String toString() {
        return "PlayerCharacterRequest{" +
                "name='" + name + '\'' +
                ", sortDirection='" + sortDirection + '\'' +
                ", page=" + page +
                ", size=" + size +
                ", sortBy='" + sortBy + '\'' +
                '}';
    }
}