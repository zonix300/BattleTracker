package com.zonix.dndapp.service;

import com.zonix.dndapp.dto.request.LobbyActionRequest;
import com.zonix.dndapp.dto.response.LobbyActionResponse;
import com.zonix.dndapp.entity.*;
import com.zonix.dndapp.repository.*;
import jakarta.transaction.Transactional;
import org.apache.coyote.BadRequestException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LobbyRequestService {
    private final LobbyRequestRepository lobbyRequestRepository;
    private final UserRepository userRepository;
    private final UserCombatantRepository userCombatantRepository;
    private final LobbyService lobbyService;

    public LobbyRequestService(LobbyRequestRepository lobbyRequestRepository, UserRepository userRepository, BattleRepository battleRepository, LobbyRepository lobbyRepository, UserCombatantRepository userCombatantRepository, LobbyService lobbyService) {
        this.lobbyRequestRepository = lobbyRequestRepository;
        this.userRepository = userRepository;
        this.userCombatantRepository = userCombatantRepository;
        this.lobbyService = lobbyService;
    }

    @Transactional
    public LobbyActionResponse createLobbyRequest(Long lobbyId, String email, LobbyActionRequest request) {
        User initiator = userRepository.findByEmail(email)
                .orElseThrow(() -> new AccessDeniedException("User not found"));

        Lobby lobby = lobbyService.findLobbyById(lobbyId)
                .orElseThrow(() -> new AccessDeniedException("Lobby was not found"));

        UserCombatant target = null;
        if (request.targetId() != null) {
            target = userCombatantRepository.findById(request.targetId())
                    .orElse(null);
        }

        LobbyRequest lobbyRequest = new LobbyRequest(
                lobby,
                initiator,
                request.type(),
                target,
                request.value(),
                request.comment(),
                LobbyRequestStatus.PENDING
        );

        save(lobbyRequest);

        return new LobbyActionResponse(lobbyRequest);
    }

    public void save(LobbyRequest request) {
        lobbyRequestRepository.save(request);
    }
}
