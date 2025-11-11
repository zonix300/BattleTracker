package com.zonix.dndapp.dto.entity;

import com.zonix.dndapp.entity.playerCharacter.PlayerCharacter;
import com.zonix.dndapp.entity.playerCharacter.helperType.Combat;
import com.zonix.dndapp.entity.playerCharacter.helperType.Inventory;
import com.zonix.dndapp.entity.playerCharacter.helperType.Sense;

import java.util.List;
import java.util.Map;

public record PlayerCharacterDTO(
        Long id,
        String name,
        Integer level,
        Integer proficiencyBonus,
        String clazz,
        Integer experience,
        String species,
        Integer armorClass,
        Map<String, Integer> abilities,
        Map<String, Object> skills,
        Map<String, Integer> hp,
        Map<String, Integer> speed,
        Combat combat,
//        List<Map<String, Object>> spells,
        Inventory inventory,
        List<Map<String, Object>> featuresTraits,
        List<Map<String, Object>> notes,
        Map<String, Object> about,
        Object defenses,
        Object conditions,
        Map<String, Map<String, Integer>> senses,
        List<Map<String, Object>> proficiencies,
        String languages
        ) {

    public PlayerCharacterDTO(PlayerCharacter pc) {
        this(pc.getId(),
                pc.getName(),
                pc.getLevel(),
                pc.getProficiencyBonus(),
                pc.getClazz(),
                pc.getExperience(),
                pc.getSpecies(),
                pc.getArmorClass(),
                pc.getAbilities(),
                pc.getSkills(),
                pc.getHp(),
                pc.getSpeed(),
                pc.getCombat(),
                pc.getInventory(),
                pc.getFeaturesTraits(),
                pc.getNotes(),
                pc.getAbout(),
                null,
                null,
                pc.getSenses(),
                pc.getProficiencies(),
                pc.getLanguages()
        );
    }
    
    @Override
    public String toString() {
        return "PlayerCharacterDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", level=" + level +
                ", proficiencyBonus=" + proficiencyBonus +
                ", clazz='" + clazz + '\'' +
                ", experience=" + experience +
                ", species='" + species + '\'' +
                ", abilities=" + abilities +
                ", skills=" + skills +
                ", hpData=" + hp +
                ", speed=" + speed +
                ", combat=" + combat +
//                ", spells=" + spells +
                ", inventory=" + inventory +
                ", featureTraits=" + featuresTraits +
                ", notes=" + notes +
                ", about=" + about +
                ", defenses=" + defenses +
                ", conditions=" + conditions +
                ", senses=" + senses +
                ", proficiencies=" + proficiencies +
                ", languages=" + languages +
                '}';
    }
}
