package com.zonix.dndapp.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public record FragmentRenderRequest(
        @JsonProperty("template") String template,
        @JsonProperty("context") Map<String, Object> context,
        @JsonProperty("combatantId") Long combatantId) {

    @JsonCreator
    public FragmentRenderRequest {
        Objects.requireNonNull(template, "Template can't be null");
        context = context != null ? context : new HashMap<>();
    }
}