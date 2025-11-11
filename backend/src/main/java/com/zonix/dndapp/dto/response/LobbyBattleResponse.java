package com.zonix.dndapp.dto.response;

public record LobbyBattleResponse (
    LobbyResponse lobbyResponse,
    BattleResponse battleResponse
) {
}
