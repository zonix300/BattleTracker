package com.zonix.dndapp.controller;

import com.zonix.dndapp.dto.entity.AuthContext;
import com.zonix.dndapp.dto.entity.PlayerCharacterDTO;
import com.zonix.dndapp.dto.entity.combatant.PlayerCombatantDTO;
import com.zonix.dndapp.dto.request.PlayerCharacterSearchRequest;
import com.zonix.dndapp.dto.response.BattleResponse;
import com.zonix.dndapp.entity.playerCharacter.PlayerCharacter;
import com.zonix.dndapp.entity.User;
import com.zonix.dndapp.service.BattleService;
import com.zonix.dndapp.service.PlayerCharacterService;
import com.zonix.dndapp.service.UserCombatantService;
import com.zonix.dndapp.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/pc")
public class PlayerCharacterController {

    private final UserService userService;
    private final PlayerCharacterService playerCharacterService;
    private final BattleService battleService;
    private final UserCombatantService userCombatantService;

    public PlayerCharacterController(UserService userService, PlayerCharacterService playerCharacterService, BattleService battleService, UserCombatantService userCombatantService) {
        this.userService = userService;
        this.playerCharacterService = playerCharacterService;
        this.battleService = battleService;
        this.userCombatantService = userCombatantService;
    }

    @GetMapping
    public ResponseEntity<?> getPlayerCharacters(
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        Optional<User> user = userService.findUserByEmail(userDetails.getUsername());

        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        List<PlayerCharacterDTO> characters = playerCharacterService.getUserPlayerCharacters(user.get());

        return ResponseEntity.ok(characters);
    }

    @PostMapping
    public ResponseEntity<?> createPlayerCharacter(
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        Optional<User> user = userService.findUserByEmail(userDetails.getUsername());
        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        PlayerCharacter pc = playerCharacterService.create(user.get());

        return ResponseEntity.status(HttpStatus.CREATED).body(pc.getId());

    }

    @GetMapping("/{pcId}")
    public ResponseEntity<?> getPlayerCharacter(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long pcId
    ) {

        Optional<User> user = userService.findUserByEmail(userDetails.getUsername());

        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Optional<PlayerCharacter> character = playerCharacterService.findById(pcId);

        if (character.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(character.get());
    }

    @PutMapping("/{pcId}")
    public ResponseEntity<?> editPlayerCharacter(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long pcId,
            @RequestBody PlayerCharacterDTO dto
    ) {
        Optional<User> user = userService.findUserByEmail(userDetails.getUsername());

        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Optional<PlayerCharacter> character = playerCharacterService.edit(pcId, dto);
        if (character.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{pcId}")
    public ResponseEntity<?> deletePlayerCharacter(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long pcId
    ) {
        Optional<User> user = userService.findUserByEmail(userDetails.getUsername());

        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        List<PlayerCharacter> characters = playerCharacterService.delete(pcId, user.get());

        return ResponseEntity.ok(characters);
    }

    @PatchMapping("/{pcId}/add")
    public ResponseEntity<?> addPlayerCharacterToBattle(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam(name = "battleId") Long battleId,
            @PathVariable Long pcId
    ) {
        AuthContext authContext = AuthContext.resolve(userDetails, "");

        BattleResponse response = userCombatantService.addPlayerCharacter(pcId, Optional.of(battleId), authContext);

        return ResponseEntity.ok(response);

    }

    @PostMapping("/search")
    public ResponseEntity<?> searchPlayerCharacter( //todo user authentication | authorization on search
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody PlayerCharacterSearchRequest request
    ) {

        Optional<User> user = userService.findUserByEmail(userDetails.getUsername());
        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Page<PlayerCombatantDTO> combatants = playerCharacterService.search(request, user.get());

        return ResponseEntity.ok(combatants);
    }
}
