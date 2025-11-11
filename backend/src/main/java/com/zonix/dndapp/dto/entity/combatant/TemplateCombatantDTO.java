package com.zonix.dndapp.dto.entity.combatant;

import com.zonix.dndapp.entity.TurnItemType;
import com.zonix.dndapp.entity.TurnQueueItem;
import com.zonix.dndapp.util.DndUtils;

public record TemplateCombatantDTO(
        long id,
        String name,
        String hitDice,
        int armorClass,
        int initiative,
        int dexterity,
        TurnItemType type
) implements TurnQueueItem {

    public TemplateCombatantDTO(
            long id,
            String name,
            String hitDice,
            int armorClass,
            int dexterity
    ) {
        this(id, name, hitDice, armorClass, DndUtils.roll(20) + DndUtils.calculateModifier(dexterity), dexterity, TurnItemType.TEMPLATE_CREATURE);
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

    public String getHitDice() {
        return hitDice;
    }
}
