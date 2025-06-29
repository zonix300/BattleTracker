package com.zonix.dndapp.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record TemplateCreatureSearchRequest(
        @NotNull @NotEmpty String name,
        @NotNull SortDirection sortDirection,
        @Min(value = 0) int page,
        @Min(value = 1) int size,
        @NotNull String sortBy
        ) {
}
