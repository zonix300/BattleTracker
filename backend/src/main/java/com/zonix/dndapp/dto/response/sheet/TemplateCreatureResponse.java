package com.zonix.dndapp.dto.response.sheet;

import java.util.Map;

public class TemplateCreatureResponse extends CreatureResponse {
    private String hitDice;
    private String challengeRating;
    private String description;
    private String legendaryDescription;
    private Map<String, String> specialAbilities;

    public String getCreatureType() {
        return creatureType;
    }

    public void setCreatureType(String creatureType) {
        this.creatureType = creatureType;
    }

    private String creatureType;

    public String getHitDice() {
        return hitDice;
    }

    public void setHitDice(String hitDice) {
        this.hitDice = hitDice;
    }

    public String getChallengeRating() {
        return challengeRating;
    }

    public void setChallengeRating(String challengeRating) {
        this.challengeRating = challengeRating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLegendaryDescription() {
        return legendaryDescription;
    }

    public void setLegendaryDescription(String legendaryDescription) {
        this.legendaryDescription = legendaryDescription;
    }


    public Map<String, String> getSpecialAbilities() {
        return specialAbilities;
    }

    public void setSpecialAbilities(Map<String, String> specialAbilities) {
        this.specialAbilities = specialAbilities;
    }
}
