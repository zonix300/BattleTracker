package com.zonix.dndapp.component;

import com.zonix.dndapp.dto.request.LobbyActionRequest;
import com.zonix.dndapp.entity.*;
import org.springframework.stereotype.Component;

@Component
public class LobbyRequestFactory {
    public static LobbyRequest fromLobbyActionRequest(Lobby lobby, User initiator, UserCombatant target, LobbyActionRequest request) {
        return new LobbyRequest(
                lobby,
                initiator,
                request.type(),
                target,
                request.value(),
                request.comment(),
                LobbyRequestStatus.PENDING

        );
    }

}
