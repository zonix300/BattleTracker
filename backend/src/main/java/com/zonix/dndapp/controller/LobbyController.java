package com.zonix.dndapp.controller;

import com.zonix.dndapp.component.LobbyRequestFactory;
import com.zonix.dndapp.dto.entity.LobbyUserDTO;
import com.zonix.dndapp.dto.request.LobbyActionRequest;
import com.zonix.dndapp.dto.request.LobbyCreationRequest;
import com.zonix.dndapp.dto.response.BattleResponse;
import com.zonix.dndapp.dto.response.LobbyBattleResponse;
import com.zonix.dndapp.dto.response.LobbyResponse;
import com.zonix.dndapp.entity.*;
import com.zonix.dndapp.exception.LobbyNotFoundException;
import com.zonix.dndapp.exception.UserNotFoundException;
import com.zonix.dndapp.repository.LobbyUserRepository;
import com.zonix.dndapp.service.*;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/lobbies")
public class LobbyController {
    private static final Logger log = LoggerFactory.getLogger(LobbyController.class);
    private final UserService userService;
    private final LobbyService lobbyService;
    private final UserCombatantService userCombatantService;
    private final LobbyRequestService lobbyRequestService;
    private final BattleService battleService;
    private final LobbyUserRepository lobbyUserRepository;
    private final SimpMessagingTemplate brokerMessagingTemplate;

    public LobbyController(UserService userService, LobbyService lobbyService, UserCombatantService userCombatantService, LobbyRequestService lobbyRequestService, BattleService battleService, LobbyUserRepository lobbyUserRepository, SimpMessagingTemplate brokerMessagingTemplate) {
        this.userService = userService;
        this.lobbyService = lobbyService;
        this.userCombatantService = userCombatantService;
        this.lobbyRequestService = lobbyRequestService;
        this.battleService = battleService;
        this.lobbyUserRepository = lobbyUserRepository;
        this.brokerMessagingTemplate = brokerMessagingTemplate;
    }

    @GetMapping
    public ResponseEntity<?> getLobbies(
            @RequestParam(value = "query", defaultValue = "") String query,
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        log.info("Get lobbies request with query: {}", query);
        Page<LobbyResponse> page = lobbyService.getLobbies(query, pageable);
        return ResponseEntity.ok(page);
    }

    @PostMapping
    public ResponseEntity<?> createLobby(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody LobbyCreationRequest request
    ) {
        log.info("Add lobby request from: {}, with content: {}", userDetails.getUsername(), request);

        Optional<User> owner = userService.findUserByEmail(userDetails.getUsername());
        if (owner.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        if (lobbyService.isUserMemberOfAnyLobby(owner.get())) {
            lobbyService.removeUserFromLobby(owner.get());
        }

        Lobby lobby = lobbyService.create(owner.get(), request);
        LobbyUser lobbyUser = lobbyService.addUserToLobby(owner.get(), lobby, LobbyRole.DM);
        battleService.assignBattleToLobby(Optional.empty(), lobby);

        List<LobbyUserDTO> dto = new ArrayList<>();
        dto.add(new LobbyUserDTO(
                lobbyUser.getUser().getUsername(),
                lobbyUser.getRole()
        ));

        return ResponseEntity.ok(new LobbyResponse(lobby.getId(), lobby.getName(), owner.get().getUsername(), lobby.getCreatedAt(), dto, !request.password().isBlank()));
    }

    @GetMapping("/{lobbyId}")
    public ResponseEntity<?> getLobby(
            @PathVariable Long lobbyId,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        Optional<User> user = userService.findUserByEmail(userDetails.getUsername());
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Optional<Lobby> lobby = lobbyService.findLobbyById(lobbyId);
        if (lobby.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        LobbyResponse lobbyResponse = lobbyService.getLobbyResponse(lobbyId);

        return ResponseEntity.ok(lobbyResponse);
    }

    @GetMapping("/{lobbyId}/battle")
    public ResponseEntity<?> getLobbyBattle(
            @PathVariable Long lobbyId,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        log.info("Get battle and lobby data request from {}, for lobby: {}", userDetails.getUsername(), lobbyId);

        User user = userService.findUserByEmail(userDetails.getUsername())
                .orElseThrow(() -> new UserNotFoundException("User not found with email"));

        Lobby lobby = lobbyService.findLobbyById(lobbyId)
                .orElseThrow(() -> new LobbyNotFoundException("Lobby not found with id"));
        log.info("User id: {}", user.getId());
        log.info("LobbyUser user id: {}", lobbyUserRepository.findLobbyUserByUser(user).getUser().getId());
        if (lobbyService.existsLobbyUserByUser(user)) {
            LobbyResponse lobbyResponse = lobbyService.getLobbyResponse(lobbyId);
            battleService.sortUserCombatants(lobby.getBattle());
            BattleResponse battleResponse = new BattleResponse(lobby.getBattle());
            LobbyBattleResponse lobbyBattleResponse = new LobbyBattleResponse(lobbyResponse, battleResponse);

            return ResponseEntity.ok(lobbyBattleResponse);
        }

        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @PostMapping("/{lobbyId}/battle")
    public ResponseEntity<?> assignBattleToLobby(
            @PathVariable Long lobbyId,
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody(required = false) Optional<Long> battleId
    ) {
        log.info("Assign battle to lobby request from {}, for lobby: {}, for battle: {}", userDetails.getUsername(), lobbyId, battleId);

        Optional<User> user = userService.findUserByEmail(userDetails.getUsername());
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Optional<Lobby> lobby = lobbyService.findLobbyById(lobbyId);
        if (lobby.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Lobby toSave = battleService.assignBattleToLobby(battleId, lobby.get());

        lobbyService.save(toSave);

        LobbyResponse response = lobbyService.getLobbyResponse(lobbyId);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/{lobbyId}/requests")
    public ResponseEntity<?> addRequest(
            @PathVariable Long lobbyId,
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody LobbyActionRequest request
    ) {
        log.info("Add lobby request request from {}, with content: {}, for lobby: {}", userDetails.getUsername(), request, lobbyId);

        Optional<Lobby> lobby = lobbyService.findLobbyById(lobbyId);
        if (lobby.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Optional<User> user = userService.findUserByEmail(userDetails.getUsername());
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Optional<UserCombatant> target = userCombatantService.findByIdAndOwner(request.targetId(), user.get());
        if (target.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        LobbyRequest lobbyRequest = LobbyRequestFactory.fromLobbyActionRequest(lobby.get(), user.get(), target.get(), request);

        lobbyRequestService.save(lobbyRequest);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/{lobbyId}/connect")
    public ResponseEntity<?> connect(
            @PathVariable Long lobbyId,
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody String password
    ) {
        log.info("Connect to lobby request from {}, for lobby: {}", userDetails.getUsername(), lobbyId);
        System.out.println("Hashed password: " + lobbyService.hashPassword(password));

        Optional<Lobby> lobby = lobbyService.findLobbyById(lobbyId);
        if (lobby.isEmpty()) {
            log.warn("Connect to lobby request failed because lobby is empty");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Optional<User> user = userService.findUserByEmail(userDetails.getUsername());
        if (user.isEmpty()) {
            log.warn("Connect to lobby request failed because user was not found");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        if (!lobbyService.validateLobbyPassword(lobby.get(), password)) {
            log.warn("Connect to lobby request failed because lobby's password is not right");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        LobbyUser lobbyUser = lobbyService.addUserToLobby(user.get(), lobby.get(), LobbyRole.PLAYER);

        Battle battle = lobbyUser.getLobby().getBattle();
        if (battle == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Lobby is not linked to any battle yet");
        }


        BattleResponse battleResponse = new BattleResponse(battle);
        LobbyResponse lobbyResponse = lobbyService.getLobbyResponse(lobbyId);

        LobbyBattleResponse lobbyBattleResponse = new LobbyBattleResponse(lobbyResponse, battleResponse);
        return ResponseEntity.ok(lobbyBattleResponse);
    }

    @DeleteMapping("/{lobbyId}/leave")
    public ResponseEntity<?> leaveLobby(
            @PathVariable Long lobbyId,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        log.info("Leave from lobby request from {}, for lobby: {}", userDetails.getUsername(), lobbyId);
        User user = userService.findUserByEmail(userDetails.getUsername())
                .orElseThrow();

        LobbyUser lobbyUser = lobbyUserRepository.findLobbyUserByUser(user);

        lobbyUserRepository.delete(lobbyUser);

        LobbyResponse response = lobbyService.getLobbyResponse(lobbyId);
        if (response != null) {
            brokerMessagingTemplate.convertAndSend(
                    "/topic/lobby/" + lobbyId,
                    response
            );
        }
        
        return ResponseEntity.ok().build();
    }

}
