package com.zonix.dndapp.entity;

import jakarta.persistence.*;

@Entity
public class Combatant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "combatant_id")
    private TemplateCreature templateCreature;



    private Integer currentHp;
    private Integer turnOrder;

    public Combatant() {
    }

    public Combatant(TemplateCreature templateCreature, Integer turnOrder) {
        this.templateCreature = templateCreature;
        this.turnOrder = turnOrder;
        this.currentHp = templateCreature.getHp();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TemplateCreature getCombatant() {
        return templateCreature;
    }

    public void setCombatant(TemplateCreature templateCreature) {
        this.templateCreature = templateCreature;
    }

    public Integer getCurrentHp() {
        return currentHp;
    }

    public void setCurrentHp(Integer currentHp) {
        this.currentHp = currentHp;
    }

    public Integer getTurnOrder() {
        return turnOrder;
    }

    public void setTurnOrder(Integer turnOrder) {
        this.turnOrder = turnOrder;
    }
}
