package com.zonix.dndapp.component;

import com.zonix.dndapp.entity.playerCharacter.PlayerCharacter;
import com.zonix.dndapp.entity.TemplateCreature;
import com.zonix.dndapp.entity.User;
import com.zonix.dndapp.entity.UserCombatant;
import com.zonix.dndapp.util.DndUtils;
import org.springframework.stereotype.Component;

@Component
public class UserCombatantFactory {

    public static UserCombatant fromTemplateWithHitDice(TemplateCreature creature, User user) {
        UserCombatant uc = fromTemplate(creature, user);

        uc.setMaxHp(DndUtils.roll(creature.getHitDice()));
        uc.setCurrentHp(uc.getMaxHp());

        return uc;
    }

    public static UserCombatant fromTemplateWithHitPoints(TemplateCreature creature, User user) {
        UserCombatant uc = fromTemplate(creature, user);

        uc.setMaxHp(creature.getHitPoints());
        uc.setCurrentHp(creature.getHitPoints());

        return uc;
    }

    public static UserCombatant fromPlayerCharacterWithHitPoints(PlayerCharacter character, User user) {
        UserCombatant uc = fromPlayerCharacter(character, user);

        uc.setMaxHp(character.getHp().get("max"));
        uc.setCurrentHp(character.getHp().get("current"));

        return uc;
    }

    private static UserCombatant fromTemplate(TemplateCreature creature, User user) {
        UserCombatant uc = new UserCombatant();
        uc.setOwner(user);

        uc.setName(creature.getName());
        uc.setHitDice(creature.getHitDice());
        uc.setArmorClass(creature.getArmorClass());
        uc.setDexterity(creature.getDexterity());
        uc.setInitiative(DndUtils.roll(20) + DndUtils.calculateModifier(uc.getDexterity()));
        uc.setTemplateCreature(creature);

        return uc;
    }

    private static UserCombatant fromPlayerCharacter(PlayerCharacter character, User user) {
        UserCombatant uc = new UserCombatant();
        uc.setOwner(user);

        uc.setName(character.getName());
        uc.setArmorClass(character.getArmorClass());
        uc.setDexterity(character.getAbilities().get("dexterity"));
        uc.setInitiative(DndUtils.roll(20) + DndUtils.calculateModifier(uc.getDexterity()));
        uc.setPlayerCharacter(character);

        return uc;
    }
}
