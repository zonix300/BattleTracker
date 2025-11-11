package com.zonix.dndapp.entity.playerCharacter.helperType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Sense {
    private boolean isActive;
    private String name;
    private int value;

    public Sense() {

    }

    public Sense(boolean isActive, String name, int value) {
        this.isActive = isActive;
        this.name = name;
        this.value = value;
    }

    public static Map<String, List<Sense>> emptySenses() {
        List<Sense> passiveSenses = List.of(
                new Sense(false, "investigation", 0),
                new Sense(false, "insight", 0),
                new Sense(false, "perception", 0)
        );

        List<Sense> activeSenses = List.of(
                new Sense(true, "darkvision", 0),
                new Sense(true, "blindsight", 0),
                new Sense(true, "truesight", 0),
                new Sense(true, "tremorsense", 0)
        );

        return Map.of(
                "active", activeSenses,
                "passive", passiveSenses
        );
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
