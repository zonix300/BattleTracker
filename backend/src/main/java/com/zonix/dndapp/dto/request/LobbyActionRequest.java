package com.zonix.dndapp.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zonix.dndapp.entity.LobbyRequestType;

public record LobbyActionRequest(
        LobbyRequestType type,
        @JsonProperty("target_id") Long targetId,
        String value,
        String comment
) {
}
