package com.zonix.dndapp.component;

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
}
