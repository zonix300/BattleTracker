package com.zonix.dndapp.resolver;

import com.zonix.dndapp.entity.Battle;
import com.zonix.dndapp.entity.Lobby;
import com.zonix.dndapp.entity.LobbyRequest;
import com.zonix.dndapp.entity.User;

public interface RequestResolver {
    void apply(LobbyRequest request, Lobby lobby, User user);
}
