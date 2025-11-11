package com.zonix.dndapp.dto.response;

import com.zonix.dndapp.dto.entity.LobbyUserDTO;
import com.zonix.dndapp.dto.entity.UserDTO;
import com.zonix.dndapp.entity.Lobby;
import com.zonix.dndapp.entity.LobbyRole;

import java.time.LocalDateTime;
import java.util.List;

public record LobbyResponse(
        Long id,
        String name,
        String ownerUsername,
        LocalDateTime createdAt,
        List<LobbyUserDTO> players,
        Boolean hasPassword
) {
}
