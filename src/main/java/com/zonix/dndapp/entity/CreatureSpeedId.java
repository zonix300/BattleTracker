package com.zonix.dndapp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;

import java.io.Serializable;

@Embeddable
public class CreatureSpeedId implements Serializable {
    @Column(name = "creature_id")
    private Long creatureId;
    @Column(name = "speed_name")
    private String speedName;


    public Long getCreatureId() {
        return creatureId;
    }

    public void setCreatureId(Long creatureId) {
        this.creatureId = creatureId;
    }

    public String getSpeedName() {
        return speedName;
    }

    public void setSpeedName(String speedName) {
        this.speedName = speedName;
    }
}
