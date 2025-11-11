package com.zonix.dndapp.resolver;

import com.zonix.dndapp.entity.*;
import com.zonix.dndapp.repository.LobbyUserRepository;
import com.zonix.dndapp.service.LobbyService;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ConnectRequestResolver implements RequestResolver{

    private final LobbyUserRepository lobbyUserRepository;
    private final SimpMessagingTemplate brokerMessagingTemplate;
    private final LobbyService lobbyService;

    public ConnectRequestResolver(LobbyUserRepository lobbyUserRepository, SimpMessagingTemplate brokerMessagingTemplate, LobbyService lobbyService) {
        this.lobbyUserRepository = lobbyUserRepository;
        this.brokerMessagingTemplate = brokerMessagingTemplate;
        this.lobbyService = lobbyService;
    }

    @Override
    public void apply(LobbyRequest request, Lobby lobby, User user) {
        System.out.println("Apply on connect request");
        System.out.println("Trying to find in future" + user.getEmail());

        if (!lobbyUserRepository.existsLobbyUserByUser(user)) {
            System.out.println("No such user in Lobby, adding");
            LobbyUser lobbyUser = new LobbyUser(user, lobby, LobbyRole.PLAYER);
            lobbyUserRepository.save(lobbyUser);

            brokerMessagingTemplate.convertAndSendToUser(
                    request.getInitiator().getEmail(),
                    "/queue/lobby/" + lobby.getId() +"/request/decision",
                    Map.of(
                            "status", LobbyRequestStatus.ACCEPT,
                            "requestId", request.getId()
                    )
            );
        }

    }
}
