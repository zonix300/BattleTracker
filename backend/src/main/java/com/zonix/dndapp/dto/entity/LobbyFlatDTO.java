package com.zonix.dndapp.dto.entity;

import com.zonix.dndapp.entity.LobbyRole;

import java.time.LocalDateTime;

public class LobbyFlatDTO {
    private Long lobbyId;
    private String lobbyName;
    private String ownerUsername;
    private LocalDateTime createdAt;
    private String userUsername;
    private LobbyRole userRole;
    private Boolean hasPassword;

    public LobbyFlatDTO(Long lobbyId, String lobbyName, String ownerUsername, LocalDateTime createdAt, String userUsername, LobbyRole userRole, Boolean hasPassword) {
        this.lobbyId = lobbyId;
        this.lobbyName = lobbyName;
        this.ownerUsername = ownerUsername;
        this.createdAt = createdAt;
        this.userUsername = userUsername;
        this.userRole = userRole;
        this.hasPassword = hasPassword;
    }


    public Long getLobbyId() {
        return lobbyId;
    }

    public void setLobbyId(Long lobbyId) {
        this.lobbyId = lobbyId;
    }

    public String getLobbyName() {
        return lobbyName;
    }

    public void setLobbyName(String lobbyName) {
        this.lobbyName = lobbyName;
    }

    public String getOwnerUsername() {
        return ownerUsername;
    }

    public void setOwnerUsername(String ownerUsername) {
        this.ownerUsername = ownerUsername;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getUserUsername() {
        return userUsername;
    }

    public void setUserUsername(String userUsername) {
        this.userUsername = userUsername;
    }

    public LobbyRole getUserRole() {
        return userRole;
    }

    public void setUserRole(LobbyRole userRole) {
        this.userRole = userRole;
    }

    public Boolean getHasPassword() {
        return hasPassword;
    }

    public void setHasPassword(Boolean hasPassword) {
        this.hasPassword = hasPassword;
    }
}
