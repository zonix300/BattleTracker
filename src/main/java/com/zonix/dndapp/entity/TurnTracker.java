package com.zonix.dndapp.entity;

import jakarta.persistence.*;

@Entity
public class TurnTracker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "combatant_id")
    private Combatant combatant;

    private Integer turnOrder;

    public TurnTracker() {
    }

    public TurnTracker(Combatant combatant, Integer turnOrder) {
        this.combatant = combatant;
        this.turnOrder = turnOrder;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Combatant getCombatant() {
        return combatant;
    }

    public void setCombatant(Combatant combatant) {
        this.combatant = combatant;
    }

    public Integer getTurnOrder() {
        return turnOrder;
    }

    public void setTurnOrder(Integer turnOrder) {
        this.turnOrder = turnOrder;
    }
}
