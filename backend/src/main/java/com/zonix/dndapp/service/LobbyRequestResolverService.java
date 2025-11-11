package com.zonix.dndapp.service;

import com.zonix.dndapp.dto.entity.AuthContext;
import com.zonix.dndapp.entity.*;
import com.zonix.dndapp.exception.LobbyNotFoundException;
import com.zonix.dndapp.repository.LobbyRepository;
import com.zonix.dndapp.repository.LobbyRequestRepository;
import com.zonix.dndapp.resolver.LobbyRequestResolverRegistry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LobbyRequestResolverService {

    private final UserService userService;
    private final BattleService battleService;
    private final LobbyRequestRepository lobbyRequestRepository;
    private final LobbyRequestResolverRegistry lobbyRequestResolverRegistry;
    private final LobbyService lobbyService;
    private final LobbyRepository lobbyRepository;

    public LobbyRequestResolverService(UserService userService, BattleService battleService, LobbyRequestRepository lobbyRequestRepository, LobbyRequestResolverRegistry lobbyRequestResolverRegistry, LobbyService lobbyService, LobbyRepository lobbyRepository) {
        this.userService = userService;
        this.battleService = battleService;
        this.lobbyRequestRepository = lobbyRequestRepository;
        this.lobbyRequestResolverRegistry = lobbyRequestResolverRegistry;
        this.lobbyService = lobbyService;
        this.lobbyRepository = lobbyRepository;
    }

    public LobbyRequest resolveDecision(Long lobbyId, Long requestId, UserDetails userDetails, LobbyRequestStatus status) {
        LobbyRequest lobbyRequest = lobbyRequestRepository.findById(requestId)
                .orElseThrow();
        lobbyRequest.setStatus(status);
        if (status == LobbyRequestStatus.ACCEPT) {
            User user = lobbyRequest.getInitiator();
            Lobby lobby = lobbyRepository.findById(lobbyId)
                    .orElseThrow(() -> new LobbyNotFoundException("Lobby not found by id"));
            lobbyRequestResolverRegistry.apply(lobbyRequest, lobby, user);
        }
        lobbyRequestRepository.save(lobbyRequest);
        return lobbyRequest;
    }
}
