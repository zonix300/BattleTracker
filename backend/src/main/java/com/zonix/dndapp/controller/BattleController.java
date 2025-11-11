package com.zonix.dndapp.controller;

import com.zonix.dndapp.dto.entity.AuthContext;
import com.zonix.dndapp.dto.response.BattleResponse;
import com.zonix.dndapp.entity.Role;
import com.zonix.dndapp.service.BattleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/battles")
public class BattleController {

    private final BattleService battleService;

    public BattleController(BattleService battleService) {
        this.battleService = battleService;
    }

    @GetMapping
    public ResponseEntity<?> getBattle(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestHeader(value = "X-Guest-Id", required = false) String sessionToken) {

        AuthContext ac = AuthContext.resolve(userDetails, sessionToken);
        BattleResponse response = battleService.getBattleResponse(Optional.empty(), ac);

        return response == null
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(response);
    }

    @GetMapping("/{battleId}")
    public ResponseEntity<?> getBattle(@PathVariable Optional<Long> battleId,
                                       @AuthenticationPrincipal UserDetails userDetails,
                                       @RequestHeader(value = "X-Guest-Id", required = false) String sessionToken) {


        AuthContext ac = AuthContext.resolve(userDetails, sessionToken);
        BattleResponse response = battleService.getBattleResponse(battleId, ac);

        return response == null
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(response);
    }

    @GetMapping("/next-turn")
    public ResponseEntity<?> nextTurn(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestHeader(value = "X-Guest-Id", required = false) String sessionToken) {

        AuthContext ac = AuthContext.resolve(userDetails, sessionToken);

        BattleResponse response = battleService.nextTurn(ac);

        return ResponseEntity.ok(response);
    }
}
