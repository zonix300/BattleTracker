package com.zonix.dndapp.util;

public enum Skill {
    ACROBATICS("acrobatics", "dexterity"),
    ANIMAL_HANDLING("animal handling", "wisdom"),
    ARCANA("arcana", "intelligence"),
    ATHLETICS("athletics", "strength"),
    DECEPTION("deception", "charisma"),
    HISTORY("history", "intelligence"),
    INSIGHT("insight", "wisdom"),
    INTIMIDATION("intimidation", "charisma"),
    INVESTIGATION("investigation", "intelligence"),
    MEDICINE("medicine", "wisdom"),
    NATURE("nature", "intelligence"),
    PERCEPTION("perception", "wisdom"),
    PERFORMANCE("performance", "charisma"),
    PERSUASION("persuasion", "charisma"),
    RELIGION("religion", "intelligence"),
    SLEIGHT_OF_HAND("sleight of hand", "dexterity"),
    STEALTH("stealth", "dexterity"),
    SURVIVAL("survival", "wisdom");

    private final String name;
    private final String ability;

    Skill(String name, String ability) {
        this.name = name;
        this.ability = ability;
    }

    public String getName() {
        return name;
    }

    public String getAbility() {
        return ability;
    }
}
