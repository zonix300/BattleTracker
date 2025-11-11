package com.zonix.dndapp.entity.playerCharacter.helperType;

public class Damage {
    private String type;
    private String ability;
    private int otherBonuses;
    private int diceNumber;
    private int diceType;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAbility() {
        return ability;
    }

    public void setAbility(String ability) {
        this.ability = ability;
    }

    public int getOtherBonuses() {
        return otherBonuses;
    }

    public void setOtherBonuses(int otherBonuses) {
        this.otherBonuses = otherBonuses;
    }

    public int getDiceNumber() {
        return diceNumber;
    }

    public void setDiceNumber(int diceNumber) {
        this.diceNumber = diceNumber;
    }

    public int getDiceType() {
        return diceType;
    }

    public void setDiceType(int diceType) {
        this.diceType = diceType;
    }
}
