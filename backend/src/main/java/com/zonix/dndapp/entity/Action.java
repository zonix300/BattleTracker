package com.zonix.dndapp.entity;

import com.zonix.dndapp.util.BaseAction;
import jakarta.persistence.*;

@Entity
@Table(name = "actions")
public class Action implements BaseAction {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String description;
    private String attackBonus;
    private String damageDice;

    public Action() {}

    public Action(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAttackBonus() {
        return attackBonus;
    }

    public void setAttackBonus(String attackBonus) {
        this.attackBonus = attackBonus;
    }

    public String getDamageDice() {
        return damageDice;
    }

    public void setDamageDice(String damageDice) {
        this.damageDice = damageDice;
    }
}
