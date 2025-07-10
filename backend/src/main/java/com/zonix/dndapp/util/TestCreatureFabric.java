package com.zonix.dndapp.util;

import com.zonix.dndapp.entity.TemplateCreature;

public class TestCreatureFabric {
    public static TemplateCreature createBasicCreature() {
        TemplateCreature creature = new TemplateCreature();
        creature.setName("Test Goblin");
        creature.setHitDice("2d6");
        creature.setDexterity(14);
        creature.setExperiencePoints(10);
        return creature;
    }

    public static TemplateCreature createCustomCreature(
            String name,
            Integer hitPoints,
            int dexterity,
            int xp
    ) {
        TemplateCreature creature = new TemplateCreature();
        creature.setName(name);
        creature.setHitPoints(hitPoints);
        creature.setDexterity(dexterity);
        creature.setExperiencePoints(xp);
        return creature;
    }

    public static TemplateCreature createCustomCreature(
            String name,
            int dexterity,
            int xp
    ) {
        TemplateCreature creature = new TemplateCreature();
        creature.setName(name);
        creature.setHitPoints(10);
        creature.setDexterity(dexterity);
        creature.setExperiencePoints(xp);
        return creature;
    }
}
