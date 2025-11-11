package com.zonix.dndapp.entity.playerCharacter.helperType;

import java.util.List;
import java.util.Map;

public class Combat {
    private List<Action> actions;
    private List<Attack> attacks;
    private List<Object> effects;
    private List<Object> masteries;

    public static Combat emptyCombat() {
        Combat c = new Combat();
        c.actions = List.of();
        c.attacks = List.of();
        c.effects = List.of();
        c.masteries = List.of();
        return c;
    }


    public List<Action> getActions() {
        return actions;
    }

    public void setActions(List<Action> actions) {
        this.actions = actions;
    }

    public List<Attack> getAttacks() {
        return attacks;
    }

    public void setAttacks(List<Attack> attacks) {
        this.attacks = attacks;
    }

    public List<Object> getEffects() {
        return effects;
    }

    public void setEffects(List<Object> effects) {
        this.effects = effects;
    }

    public List<Object> getMasteries() {
        return masteries;
    }

    public void setMasteries(List<Object> masteries) {
        this.masteries = masteries;
    }
}
