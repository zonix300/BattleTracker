package com.zonix.dndapp.entity;

import jakarta.persistence.*;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;

@Entity
@Table(name = "lobbies")
public class Lobby {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String passwordHash;

    @OneToOne
    private User owner;

    @OneToOne
    private Battle battle;

    private LocalDateTime createdAt;

    @OneToMany(
            mappedBy = "lobby",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<LobbyRequest> requests;

    public Lobby() {}

    public Lobby(String name, String passwordHash, User owner) {
        this.name = name;
        this.passwordHash = passwordHash;
        this.owner = owner;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Battle getBattle() {
        return battle;
    }

    public void setBattle(Battle battle) {
        this.battle = battle;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    private void setRequests(List<LobbyRequest> requests) {
        this.requests = requests;
    }

    private List<LobbyRequest> getRequests() {
        return requests;
    }
}
