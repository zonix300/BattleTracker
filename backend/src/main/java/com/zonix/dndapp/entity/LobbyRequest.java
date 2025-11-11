package com.zonix.dndapp.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "lobby_requests")
public class LobbyRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Lobby lobby;

    @OneToOne
    private User initiator;

    @Enumerated(EnumType.STRING)
    private LobbyRequestType type;

    @ManyToOne
    private UserCombatant target;

    private String value;

    private String comment;


    @Enumerated(EnumType.STRING)
    private LobbyRequestStatus status;

    public LobbyRequest() {

    }

    public LobbyRequest(Lobby lobby, User initiator, LobbyRequestType type, UserCombatant target, String value, String comment, LobbyRequestStatus status) {
        this.lobby = lobby;
        this.initiator = initiator;
        this.type = type;
        this.target = target;
        this.value = value;
        this.comment = comment;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Lobby getLobby() {
        return lobby;
    }

    public void setLobby(Lobby lobby) {
        this.lobby = lobby;
    }

    public User getInitiator() {
        return initiator;
    }

    public void setInitiator(User initiator) {
        this.initiator = initiator;
    }

    public LobbyRequestType getType() {
        return type;
    }

    public void setType(LobbyRequestType type) {
        this.type = type;
    }

    public UserCombatant getTarget() {
        return target;
    }

    public void setTarget(UserCombatant target) {
        this.target = target;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LobbyRequestStatus getStatus() {
        return status;
    }

    public void setStatus(LobbyRequestStatus status) {
        this.status = status;
    }
}
