package com.zonix.dndapp.entity;

import jakarta.persistence.Embeddable;

@Embeddable
public class UserLobbyId {
    private Long userId;
    private Long lobbyId;

    public UserLobbyId() {

    }

    public UserLobbyId(User user, Lobby lobby) {
        this.userId = user.getId();
        this.lobbyId = lobby.getId();
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getLobbyId() {
        return lobbyId;
    }

    public void setLobbyId(Long lobbyId) {
        this.lobbyId = lobbyId;
    }
}
