package com.zonix.dndapp.dto.entity;

import com.zonix.dndapp.entity.LobbyRole;

public record LobbyUserDTO(
        String name,
        LobbyRole role
) {

}
