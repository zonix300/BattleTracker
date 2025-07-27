package com.zonix.dndapp.dto.response;

import com.zonix.dndapp.dto.entity.UserCombatantDTO;
import com.zonix.dndapp.entity.Battle;
import com.zonix.dndapp.entity.UserCombatant;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public record BattleResponse(
        Long id,
        String name,
        UserCombatantDTO current,
        Integer round,
        List<UserCombatantDTO> userCombatants,
        LocalDateTime createdAt
) {
    public BattleResponse(Battle battle) {
        this(
                battle.getId(),
                battle.getName(),
                battle.getCurrent() != null ? new UserCombatantDTO(battle.getCurrent()) : null,
                battle.getRound(),
                battle.getUserCombatants().stream()
                        .map(UserCombatantDTO::new)
                        .toList(),
                battle.getCreatedAt()
        );
    }
}
