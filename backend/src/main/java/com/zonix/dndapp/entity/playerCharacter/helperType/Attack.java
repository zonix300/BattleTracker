package com.zonix.dndapp.entity.playerCharacter.helperType;

public class Attack {
    private String name;
    private String type;
    private String linkedWeapon;
    private int range;
    private int hit_dc;
    private String actionType;
    private Damage damage;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLinkedWeapon() {
        return linkedWeapon;
    }

    public void setLinkedWeapon(String linkedWeapon) {
        this.linkedWeapon = linkedWeapon;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public int getHit_dc() {
        return hit_dc;
    }

    public void setHit_dc(int hit_dc) {
        this.hit_dc = hit_dc;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public Damage getDamage() {
        return damage;
    }

    public void setDamage(Damage damage) {
        this.damage = damage;
    }
}

