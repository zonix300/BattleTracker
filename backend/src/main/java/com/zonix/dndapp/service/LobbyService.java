package com.zonix.dndapp.service;

import com.zonix.dndapp.dto.entity.LobbyFlatDTO;
import com.zonix.dndapp.dto.entity.LobbyUserDTO;
import com.zonix.dndapp.dto.request.LobbyCreationRequest;
import com.zonix.dndapp.dto.response.LobbyResponse;
import com.zonix.dndapp.entity.Lobby;
import com.zonix.dndapp.entity.LobbyRole;
import com.zonix.dndapp.entity.User;
import com.zonix.dndapp.entity.LobbyUser;
import com.zonix.dndapp.exception.UserNotFoundException;
import com.zonix.dndapp.repository.LobbyRepository;
import com.zonix.dndapp.repository.LobbyUserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@Service
public class LobbyService {

    private final LobbyRepository lobbyRepository;
    private final LobbyUserRepository lobbyUserRepository;
    private final UserService userService;

    public LobbyService(LobbyRepository lobbyRepository, LobbyUserRepository lobbyUserRepository, UserService userService) {
        this.lobbyRepository = lobbyRepository;
        this.lobbyUserRepository = lobbyUserRepository;
        this.userService = userService;
    }

    public Optional<Lobby> findLobbyById(Long id) {
        return lobbyRepository.findById(id);
    }

    public Lobby create(User owner, LobbyCreationRequest request) {
        Lobby lobby = new Lobby(
                request.name(),
                hashPassword(request.password()),
                owner
        );

        lobbyRepository.save(lobby);
        return lobby;
    }

    public String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm is not available");
        }
    }

    public LobbyUser addUserToLobby(User user, Lobby lobby, LobbyRole role) {
        LobbyUser lobbyUser = new LobbyUser(user, lobby, role);

        lobbyUserRepository.save(lobbyUser);

        return lobbyUser;
    }

    public void removeUserFromLobby(User user) {
        LobbyUser lobbyUser = lobbyUserRepository.findLobbyUserByUser(user);
        lobbyUserRepository.delete(lobbyUser);
    }

    public Page<LobbyResponse> getLobbies(String query, Pageable pageable) {
        List<Long> lobbyIds = lobbyRepository.findLobbyIdsByNameStartingWith(query, pageable);
        List<LobbyFlatDTO> flatDTOList = lobbyUserRepository.getFlatLobbiesByNameStartingWith(lobbyIds);

        Map<Long, LobbyResponse> map = new HashMap<>();

        for (LobbyFlatDTO dto : flatDTOList) {
            LobbyResponse response = map.computeIfAbsent(dto.getLobbyId(), id -> new LobbyResponse(
                    id,
                    dto.getLobbyName(),
                    dto.getOwnerUsername(),
                    dto.getCreatedAt(),
                    new ArrayList<>(),
                    dto.getHasPassword()
            ));
            response.players().add(new LobbyUserDTO(dto.getUserUsername(), dto.getUserRole()));
        }

        List<LobbyResponse> responseList = new ArrayList<>(map.values());
        return new PageImpl<>(responseList, pageable, lobbyIds.size());
    }

    public LobbyResponse getLobbyResponse(Long lobbyId) {

        List<LobbyFlatDTO> flatDTOList = lobbyUserRepository.getFlatLobbiesByNameStartingWith(List.of(lobbyId));

        LobbyResponse response = null;
        for (LobbyFlatDTO dto : flatDTOList) {
            if (response == null) {
                response = new LobbyResponse(
                        lobbyId,
                        dto.getLobbyName(),
                        dto.getOwnerUsername(),
                        dto.getCreatedAt(),
                        new ArrayList<>(),
                        dto.getHasPassword()
                );
            }
            response.players().add(new LobbyUserDTO(dto.getUserUsername(), dto.getUserRole()));
        }

        return response;

    }

    public boolean validateLobbyPassword(Lobby lobby, String password) {
        if (lobby == null || password == null) {
            return false;
        }

        String storedHash = lobby.getPasswordHash();

        if (storedHash == null || storedHash.isEmpty()) {
            return false;
        }

        return lobby.getPasswordHash().equals(hashPassword(password));
    }

    public boolean existsLobbyUserByUser(User user) {
        return lobbyUserRepository.existsLobbyUserByUser(user);
    }

    public boolean isUserMemberOfLobby(User user, Lobby lobby) {
        return lobbyUserRepository.existsLobbyUserByUserAndLobby(user, lobby);
    }

    public boolean isUserMemberOfAnyLobby(User user) {
        return lobbyUserRepository.existsLobbyUserByUser(user);
    }

    public void save(Lobby lobby) {
        lobbyRepository.save(lobby);
    }
}
