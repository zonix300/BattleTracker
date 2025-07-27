package com.zonix.dndapp.controller;

import com.zonix.dndapp.dto.entity.UserCombatantDTO;
import com.zonix.dndapp.dto.request.*;
import com.zonix.dndapp.dto.response.BattleResponse;
import com.zonix.dndapp.entity.Combatant;
import com.zonix.dndapp.entity.TemplateCreature;
import com.zonix.dndapp.service.TemplateCreatureService;
import com.zonix.dndapp.service.UserCombatantService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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

    @PatchMapping("/hp")
    public ResponseEntity<?> updateHp(@Valid @RequestBody HpUpdateRequest request) {

        return ResponseEntity.ok("");
    }

    @PatchMapping("/effects")
    public ResponseEntity<Void> updateEffects(@Valid @RequestBody EffectBatchRequest request) {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{templateId}/add")
    public ResponseEntity<?> add(@PathVariable Long templateId, @RequestParam("battleId") Optional<Long> battleId, Authentication authentication) {
        String email = authentication.getName();
        log.info("Add combatant with template id: {}, for user with email: {}", templateId, email);

        BattleResponse response = userCombatantService.addCombatant(templateId, battleId, email);

        if (response == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(response);
    }

    @PostMapping("/search")
    public ResponseEntity<Page<Combatant>> search(@Valid @RequestBody TemplateCreatureSearchRequest request) {
        log.info("Search request: {}", request);

        Page<Combatant> response;
        response = templateCreatureService.search(request);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{combatantId}/initiative-roll")
    public ResponseEntity<?> rollInitiative(@PathVariable Long combatantId, Authentication authentication) {
        String email = authentication.getName();
        log.info("Rolling initiative for combatant with id: {}, for user with email: {}", combatantId, email);

        UserCombatantDTO response = userCombatantService.rollInitiative(combatantId, email);

        if (response == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{templateId}")
    public ResponseEntity<TemplateCreature> get(@PathVariable Long templateId) {
        TemplateCreature creature = templateCreatureService.findTemplateCreatureById(templateId);
        return ResponseEntity.ok(creature);
    }

    @PutMapping("/{combatantId}")
    public ResponseEntity<?> updateCombatant(@PathVariable Long combatantId, @RequestBody CombatantUpdateRequest request, Authentication authentication) {
        String email = authentication.getName();
        log.info("Updating combatant by id: {} with {}, for user with email: {}", combatantId, request, email);

        BattleResponse response = userCombatantService.updateCombatant(combatantId, request, email);

        if (response == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{combatantId}")
    public ResponseEntity<?> deleteCombatant(@PathVariable Long combatantId, Authentication authentication) {
        String email = authentication.getName();
        log.info("Deleting combatant by id: {}, for user with email: {}", combatantId, email);

        BattleResponse response = userCombatantService.deleteCombatant(combatantId, email);

        if (response == null) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(response);
    }
}
