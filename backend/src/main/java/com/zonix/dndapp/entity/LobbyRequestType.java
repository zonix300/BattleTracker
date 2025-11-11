package com.zonix.dndapp.entity;

public enum LobbyRequestType {
    CONNECT,
    DISCONNECT,
    HEAL,
    DAMAGE,
    INITIATIVE,
    ROLL_INITIATIVE,
    EFFECT;

    public boolean isBattleConnected() {
        return this == HEAL
                || this == DAMAGE
                || this == INITIATIVE
                || this == ROLL_INITIATIVE
                || this == EFFECT;
    }

    public boolean isLobbyConnected() {
        return this == CONNECT
                || this == DISCONNECT;
    }
}
