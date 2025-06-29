package com.zonix.dndapp.util;

import com.zonix.dndapp.entity.Combatant;
import com.zonix.dndapp.entity.TemplateCreature;

import java.util.Map;

public class CombatantBuilder {
    private Long id = 0L;
    private String name = "Default name";
    private Integer currentHp = 10;
    private Integer maxHp = 10;
    private int experiencePoints = 0;

    public static CombatantBuilder aCombatant() {
        return new CombatantBuilder();
    }

    public CombatantBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public CombatantBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public CombatantBuilder withMaxHp(int maxHp) {
        this.maxHp = maxHp;
        this.currentHp = Math.min(currentHp, maxHp);
        return this;
    }

    public CombatantBuilder withCurrentHp(int currentHp) {
        this.currentHp = Math.min(currentHp, maxHp);
        return this;
    }

    public CombatantBuilder withExperiencePoints(int xp) {
        this.experiencePoints = xp;
        return this;
    }

    public Combatant build() {
        TemplateCreature templateCreature = TestCreatureFabric.createCustomCreature(name, maxHp, experiencePoints);
        Combatant combatant = new Combatant(templateCreature);
        combatant.setMaxHp(maxHp);
        combatant.setCurrentHp(currentHp);
        combatant.setId(id);
        return combatant;
    }
}
