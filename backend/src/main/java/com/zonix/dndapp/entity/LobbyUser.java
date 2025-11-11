package com.zonix.dndapp.entity;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "lobby_users")
public class LobbyUser {
    @EmbeddedId
    private UserLobbyId id;

    @ManyToOne
    @MapsId("userId")
    private User user;

    @ManyToOne
    @MapsId("lobbyId")
    private Lobby lobby;

    @Enumerated(EnumType.STRING)
    private LobbyRole role;

    private Instant disconnectTime;

    public LobbyUser() {

    }

    public LobbyUser(User user, Lobby lobby, LobbyRole role) {
        this.id = new UserLobbyId(user, lobby);
        this.user = user;
        this.lobby = lobby;
        this.role = role;
    }
    public UserLobbyId getId() {
        return id;
    }

    public void setId(UserLobbyId id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Lobby getLobby() {
        return lobby;
    }

    public void setLobby(Lobby lobby) {
        this.lobby = lobby;
    }

    public LobbyRole getRole() {
        return role;
    }

    public void setRole(LobbyRole role) {
        this.role = role;
    }

    public Instant getDisconnectTime() {
        return disconnectTime;
    }

    public void setDisconnectTime(Instant disconnectTime) {
        this.disconnectTime = disconnectTime;
    }
}
