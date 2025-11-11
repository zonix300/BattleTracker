package com.zonix.dndapp.dto.response;

public record JoinRequest (
    String username,
    Long lobbyId
) {

}
