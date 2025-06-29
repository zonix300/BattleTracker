package com.zonix.dndapp.dto.entity;


import com.zonix.dndapp.util.DndUtils;

public class CreatureSummaryDto {
    Long id;
    String name;
    Integer ac;
    Integer initiative;
    Integer maxHitPoints;
    Integer currentHitPoints;
    String hitDice;
    Integer dexterity;

    public CreatureSummaryDto() {
    }

    public CreatureSummaryDto(Long id, String name, Integer ac, String hitDice, Integer dexterity) {
        this.id = id;
        this.name = name;
        this.ac = ac;
        this.hitDice = hitDice;
        this.dexterity = dexterity;
        this.initiative = DndUtils.roll(20) + dexterity;
        this.maxHitPoints = DndUtils.roll(hitDice);
        this.currentHitPoints = this.maxHitPoints;
    }


}
