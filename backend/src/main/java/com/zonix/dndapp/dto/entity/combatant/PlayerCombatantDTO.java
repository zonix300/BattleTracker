package com.zonix.dndapp.dto.entity.combatant;

import com.zonix.dndapp.entity.TurnItemType;
import com.zonix.dndapp.entity.TurnQueueItem;
import com.zonix.dndapp.util.DndUtils;

public record PlayerCombatantDTO(
        long id,
        String name,
        int currentHp,
        int maxHp,
        int temporaryHp,
        int armorClass,
        int initiative,
        int dexterity,
        TurnItemType type
) implements TurnQueueItem {

    public PlayerCombatantDTO(
            long id,
            String name,
            int currentHp,
            int maxHp,
            int temporaryHp,
            int armorClass,
            int dexterity
    ) {
        this(
                id,
                name,
                currentHp,
                maxHp,
                temporaryHp,
                armorClass,
                DndUtils.roll(20) + DndUtils.calculateModifier(dexterity),
                dexterity,
                TurnItemType.PLAYER_CHARACTER);
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getInitiative() {
        return initiative;
    }

    @Override
    public int getDexterity() {
        return dexterity;
    }

    @Override
    public TurnItemType getType() {
        return type;
    }
}
