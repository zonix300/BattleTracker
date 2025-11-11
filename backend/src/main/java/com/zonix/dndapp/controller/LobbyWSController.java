package com.zonix.dndapp.controller;

import com.zonix.dndapp.dto.entity.AuthContext;
import com.zonix.dndapp.dto.request.LobbyActionRequest;
import com.zonix.dndapp.dto.response.BattleResponse;
import com.zonix.dndapp.dto.response.LobbyActionResponse;
import com.zonix.dndapp.dto.response.LobbyBattleResponse;
import com.zonix.dndapp.dto.response.LobbyResponse;
import com.zonix.dndapp.entity.*;
import com.zonix.dndapp.exception.LobbyNotFoundException;
import com.zonix.dndapp.repository.LobbyUserRepository;
import com.zonix.dndapp.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;

import java.util.Map;
import java.util.Optional;

@Controller
public class LobbyWSController {

    private static final Logger log = LoggerFactory.getLogger(LobbyWSController.class);
    private final LobbyRequestService lobbyRequestService;
    private final SimpMessagingTemplate brokerMessagingTemplate;
    private final BattleService battleService;
    private final LobbyRequestResolverService lobbyRequestResolverService;
    private final LobbyService lobbyService;
    private final UserService userService;
    private final LobbyUserRepository lobbyUserRepository;

    public LobbyWSController(LobbyRequestService lobbyRequestService, SimpMessagingTemplate brokerMessagingTemplate, BattleService battleService, LobbyRequestResolverService lobbyRequestResolverService, LobbyService lobbyService, UserService userService, LobbyUserRepository lobbyUserRepository) {
        this.lobbyRequestService = lobbyRequestService;
        this.brokerMessagingTemplate = brokerMessagingTemplate;
        this.battleService = battleService;
        this.lobbyRequestResolverService = lobbyRequestResolverService;
        this.lobbyService = lobbyService;
        this.userService = userService;
        this.lobbyUserRepository = lobbyUserRepository;
    }

    @MessageMapping("/test")
    public void testConnection(@Payload Map<String, String> message) {
        log.info("Test message received: {}", message);
    }

    @MessageMapping("/lobby/{lobbyId}/requests")
    public void handlePlayerRequest(
            @DestinationVariable Long lobbyId,
            @AuthenticationPrincipal UserDetails userDetails,
            LobbyActionRequest request
    ) {
        log.info("Battle request for lobby with id: {}, from: {}, with data: {}", lobbyId, userDetails.getUsername(), request);

        Lobby lobby = lobbyService.findLobbyById(lobbyId).orElseThrow();
        LobbyActionResponse response = lobbyRequestService.createLobbyRequest(lobbyId, userDetails.getUsername(), request);

        log.info("Response: {} will be send to user: {}", response, lobby.getOwner().getEmail());
        brokerMessagingTemplate.convertAndSendToUser(
                lobby.getOwner().getEmail(),
                "/queue/lobby/" + lobbyId + "/requests",
                response
        );
    }

    @MessageMapping("/lobby/{lobbyId}/request/{requestId}/decision")
    public void handleDmDecision(
            @DestinationVariable Long lobbyId,
            @DestinationVariable Long requestId,
            @AuthenticationPrincipal UserDetails userDetails,
            Map<String, String> payload
    ) {
        String statusStr = payload.get("status");
        LobbyRequestStatus status = LobbyRequestStatus.valueOf(statusStr);

        log.info("Decision with status: {}", status);
        LobbyRequest lobbyRequest = lobbyRequestResolverService.resolveDecision(lobbyId, requestId, userDetails, status);
        if (lobbyRequest.getType().isBattleConnected()) {
            Lobby lobby = lobbyService.findLobbyById(lobbyId)
                    .orElseThrow(() -> new LobbyNotFoundException("Lobby was not found by id"));
            BattleResponse battleResponse = new BattleResponse(lobby.getBattle());
            brokerMessagingTemplate.convertAndSend(
                    "/topic/lobby/" + lobbyId,
                    battleResponse);
        }

    }

    @MessageMapping("/lobby/{lobbyId}/rejoin")
    public void handleLobbyRejoin(
            @DestinationVariable Long lobbyId,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        String email = userDetails.getUsername();

        User user = userService.findUserByEmail(email)
                .orElseThrow();

        Lobby lobby = lobbyService.findLobbyById(lobbyId)
                .orElseThrow();

        boolean isMember = lobbyService.isUserMemberOfLobby(user, lobby);

        if (isMember) {
            log.info("User {} rejoined lobby {}", email, lobbyId);

            LobbyUser lobbyUser = lobbyUserRepository.findLobbyUserByUser(user);
            lobbyUser.setDisconnectTime(null);
            lobbyUserRepository.save(lobbyUser);
            LobbyResponse lobbyResponse = lobbyService.getLobbyResponse(lobbyId);
            BattleResponse battleResponse = new BattleResponse(lobby.getBattle());

            LobbyBattleResponse lobbyBattleResponse = new LobbyBattleResponse(lobbyResponse, battleResponse);

            brokerMessagingTemplate.convertAndSendToUser(
                    email,
                    "/queue/lobby/rejoin",
                    lobbyBattleResponse
            );
        } else {
            log.info("User {} try to rejoin lobby not being it's member", email);
        }
    }

}
