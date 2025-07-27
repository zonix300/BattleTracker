package com.zonix.dndapp.controller;

import com.zonix.dndapp.dto.response.BattleResponse;
import com.zonix.dndapp.service.BattleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/battles")
public class BattleController {

    private final BattleService battleService;

    public BattleController(BattleService battleService) {
        this.battleService = battleService;
    }

    @GetMapping
    public ResponseEntity<?> getBattle(Authentication authentication) {
        String email = authentication.getName();

        BattleResponse response = battleService.getBattle(Optional.empty(), email);

        if (response == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{battleId}")
    public ResponseEntity<?> getBattle(@PathVariable Optional<Long> battleId, Authentication authentication) {
        String email = authentication.getName();

        BattleResponse response = battleService.getBattle(battleId, email);

        if (response == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping("/next-turn")
    public ResponseEntity<?> nextTurn(Authentication authentication) {
        String email = authentication.getName();

        BattleResponse response = battleService.nextTurn(email);

        return ResponseEntity.ok(response);
    }
}
