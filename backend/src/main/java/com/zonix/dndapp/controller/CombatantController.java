package com.zonix.dndapp.controller;

import com.zonix.dndapp.dto.entity.AuthContext;
import com.zonix.dndapp.dto.entity.UserCombatantDTO;
import com.zonix.dndapp.dto.request.*;
import com.zonix.dndapp.dto.response.BattleResponse;
import com.zonix.dndapp.dto.response.sheet.CreatureResponse;
import com.zonix.dndapp.dto.response.sheet.TemplateCreatureResponse;
import com.zonix.dndapp.entity.Combatant;
import com.zonix.dndapp.entity.TemplateCreature;
import com.zonix.dndapp.entity.TurnQueueItem;
import com.zonix.dndapp.service.TemplateCreatureService;
import com.zonix.dndapp.service.UserCombatantService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/combatants")
public class CombatantController {

    private final TemplateCreatureService templateCreatureService;
    private final UserCombatantService userCombatantService;
    private final static Logger log = LoggerFactory.getLogger(CombatantController.class);

    public CombatantController(TemplateCreatureService templateCreatureService, UserCombatantService userCombatantService) {
        this.templateCreatureService = templateCreatureService;
        this.userCombatantService = userCombatantService;
    }

    @GetMapping
    public ResponseEntity<?> getCombatants(Authentication authentication) {
        String email = authentication.getName();
        log.info("Get combatants for user with email: {}", email);
        Set<UserCombatantDTO> userCombatantDTOs = userCombatantService.getUserCombatants(email);
        return ResponseEntity.ok(userCombatantDTOs);
    }

    @PatchMapping("/effects")
    public ResponseEntity<Void> updateEffects(@Valid @RequestBody EffectBatchRequest request) {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{templateId}/add")
    public ResponseEntity<?> add(
            @PathVariable Long templateId,
            @RequestParam("battleId") Optional<Long> battleId,
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestHeader(value = "X-Guest-Id", required = false) String sessionToken) {

        AuthContext ac = AuthContext.resolve(userDetails, sessionToken);

        log.info("Add combatant with template id: {}, for user with identifier: {}", templateId, ac.getIdentifier());

        BattleResponse response = userCombatantService.addCombatant(templateId, battleId, ac);

        return response == null
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(response);
    }

    @PostMapping("/search")
    public ResponseEntity<? > search(
            @Valid @RequestBody TemplateCreatureSearchRequest request
    ) { //todo change method to return Page<UserCombatantDTO>
        log.info("Search request: {}", request);

        Page<TurnQueueItem> response = templateCreatureService.search(request);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{combatantId}/initiative-roll")
    public ResponseEntity<?> rollInitiative(
            @PathVariable Long combatantId,
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestHeader(value = "X-Guest-Id", required = false) String sessionToken) {

        AuthContext ac = AuthContext.resolve(userDetails, sessionToken);

        log.info("Rolling initiative for combatant with id: {}, for user with identifier: {}", combatantId, ac.getIdentifier());

        UserCombatantDTO response = userCombatantService.rollInitiative(combatantId, ac);

        return response == null
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(response);
    }

    @GetMapping("/{combatantId}")
    public ResponseEntity<CreatureResponse> get(@PathVariable Long combatantId) {
        CreatureResponse response = userCombatantService.getCreatureResponseFromCombatantId(combatantId);
        if (response == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{combatantId}")
    public ResponseEntity<?> updateCombatant(
            @PathVariable Long combatantId,
            @RequestBody CombatantUpdateRequest request,
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestHeader(value = "X-Guest-Id", required = false) String sessionToken) {

        AuthContext ac = AuthContext.resolve(userDetails, sessionToken);

        log.info("Updating combatant by id: {} with {}, for user with identifier: {}", combatantId, request, ac.getIdentifier());

        BattleResponse response = userCombatantService.updateCombatant(combatantId, request, ac);

        return response == null
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(response);
    }

    @DeleteMapping("/{combatantId}")
    public ResponseEntity<?> deleteCombatant(
            @PathVariable Long combatantId,
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestHeader(value = "X-Guest-Id", required = false) String sessionToken) {

        AuthContext ac = AuthContext.resolve(userDetails, sessionToken);

        log.info("Deleting combatant by id: {}, for user with identifier: {}", combatantId, ac.getIdentifier());

        BattleResponse response = userCombatantService.deleteCombatant(combatantId, ac);

        return response == null
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(response);
    }
}
