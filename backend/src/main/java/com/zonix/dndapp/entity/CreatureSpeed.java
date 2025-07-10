package com.zonix.dndapp.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "creature_speeds")
public class CreatureSpeed {
    @EmbeddedId
    private CreatureSpeedId id;

    @Column(name = "speed_value")
    private Integer value;



    @ManyToOne
    @MapsId("creatureId")
    @JoinColumn(name = "creature_id")
    private TemplateCreature creature;

    public CreatureSpeedId getId() {
        return id;
    }

    public void setId(CreatureSpeedId id) {
        this.id = id;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public TemplateCreature getCreature() {
        return creature;
    }

    public void setCreature(TemplateCreature creature) {
        this.creature = creature;
    }

    public String getName() {
        return this.id.getSpeedType();
    }
}
