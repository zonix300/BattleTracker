package com.zonix.dndapp.dto.response;

import com.zonix.dndapp.dto.entity.LobbyUserDTO;
import com.zonix.dndapp.dto.entity.UserCombatantDTO;
import com.zonix.dndapp.dto.entity.UserDTO;
import com.zonix.dndapp.dto.request.LobbyActionRequest;
import com.zonix.dndapp.entity.LobbyRequest;
import com.zonix.dndapp.entity.LobbyRequestType;
import com.zonix.dndapp.entity.LobbyRole;

public record LobbyActionResponse(
        Long id,
        LobbyUserDTO initiator,
        LobbyRequestType type,
        UserCombatantDTO target,
        String value,
        String comment
) {
    public LobbyActionResponse(LobbyRequest request) {
        this(
                request.getId(),
                new LobbyUserDTO(request.getInitiator().getUsername(), LobbyRole.PLAYER),
                request.getType(),
                request.getTarget() == null ? null : new UserCombatantDTO(request.getTarget()),
                request.getValue(),
                request.getComment()
        );
    }

}
