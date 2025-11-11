package com.zonix.dndapp.mapper;

import com.zonix.dndapp.dto.response.sheet.PlayerCharacterResponse;
import com.zonix.dndapp.entity.TurnItemType;
import com.zonix.dndapp.entity.playerCharacter.PlayerCharacter;
import com.zonix.dndapp.entity.UserCombatant;
import com.zonix.dndapp.entity.playerCharacter.helperType.Action;
import com.zonix.dndapp.entity.playerCharacter.helperType.Attack;
import com.zonix.dndapp.entity.playerCharacter.helperType.Damage;

import java.util.HashMap;
import java.util.Map;

public class PlayerCharacterMapper {
    public static PlayerCharacterResponse fromUserCombatant(UserCombatant uc) {
        if (uc.getPlayerCharacter() == null) return null;
        PlayerCharacter pc = uc.getPlayerCharacter();
        PlayerCharacterResponse r = new PlayerCharacterResponse();

        r.setId(pc.getId());
        r.setName(uc.getName());
        r.setLevel(pc.getLevel());
        r.setExperience(pc.getExperience());
        r.setProficiencyBonus(pc.getProficiencyBonus());
        r.setAlignment(pc.getAlignment());
        r.setArmorClass(uc.getArmorClass());
        r.setMaxHp(uc.getMaxHp());
        r.setCurrentHp(uc.getCurrentHp());

        r.setSpeeds(pc.getSpeed());
        r.setAbilities(pc.getAbilities());
        r.setSkills(pc.getSkills().keySet());

        Map<String, String> actions = new HashMap<>();

        for (Action action : pc.getCombat().getActions()) {
            actions.put(action.getName(), action.getDescription());
        }
        for (Attack attack : pc.getCombat().getAttacks()) {
            String description = getDescription(attack);
            actions.put(attack.getName(), description);
        }
        r.setActions(Map.of("action", actions));
        r.setCreatureType(pc.getSpecies());

        if (pc.getLanguages() != null) {
            r.setLanguages(pc.getLanguages().toString());
        } else {
            r.setLanguages(null);
        }
        r.setClazz(pc.getClazz());

        r.setType(TurnItemType.PLAYER_CHARACTER);
        return r;

    }

    private static String getDescription(Attack attack) {
        Damage dmg = attack.getDamage();
        return String.format(
                "Type: %s | Weapon: %s | Range: %d ft | Hit/DC: %s%d | Damage: %dd%d %s | Action: %s",
                attack.getType() != null ? attack.getType() : "—",
                attack.getLinkedWeapon() != null ? attack.getLinkedWeapon() : "—",
                attack.getRange(),
                attack.getHit_dc() >= 0 ? "+" : "",
                attack.getHit_dc(),
                dmg != null ? dmg.getDiceNumber() : 0,
                dmg != null ? dmg.getDiceType() : 0,
                dmg != null && dmg.getType() != null ? dmg.getType() : "—",
                attack.getActionType() != null ? attack.getActionType() : "—"
        );
    }

}
