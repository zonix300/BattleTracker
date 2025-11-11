package com.zonix.dndapp.component;

import com.zonix.dndapp.entity.Lobby;
import com.zonix.dndapp.entity.LobbyUser;
import com.zonix.dndapp.entity.User;
import com.zonix.dndapp.repository.LobbyRepository;
import com.zonix.dndapp.repository.LobbyUserRepository;
import com.zonix.dndapp.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.security.Principal;
import java.time.Instant;
import java.util.List;

@Component
public class WebSocketEventListener {

    private final LobbyUserRepository lobbyUserRepository;
    private final UserRepository userRepository;
    private final LobbyRepository lobbyRepository;

    public WebSocketEventListener(LobbyUserRepository lobbyUserRepository, UserRepository userRepository, LobbyRepository lobbyRepository) {
        this.lobbyUserRepository = lobbyUserRepository;
        this.userRepository = userRepository;
        this.lobbyRepository = lobbyRepository;
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        Principal principal = event.getUser();

        if (principal != null) {
            User user = userRepository.findByEmail(principal.getName())
                    .orElseThrow();
            LobbyUser lobbyUser = lobbyUserRepository.findLobbyUserByUser(user);
            if (lobbyUser == null) {
                return;
            }
            lobbyUser.setDisconnectTime(Instant.now());
            lobbyUserRepository.save(lobbyUser);
        }
    }

    @Scheduled(fixedRate = 10000)
    public void cleanupDisconnectedUsers() {
        Instant cutoff = Instant.now().minusSeconds(30);
        List<LobbyUser> expired = lobbyUserRepository.findAllByDisconnectTimeBefore(cutoff);
        lobbyUserRepository.deleteAll(expired);
    }

    @Scheduled(fixedRate = 10000)
    public void cleanupDeadLobbies() {
        List<Lobby> deadLobbies = lobbyRepository.findEmptyLobbies();
        lobbyRepository.deleteAll(deadLobbies);
    }


}
