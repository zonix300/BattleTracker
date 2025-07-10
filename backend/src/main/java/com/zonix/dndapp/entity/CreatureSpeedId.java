package com.zonix.dndapp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class CreatureSpeedId implements Serializable {
    @Column(name = "creature_id")
    private Long creatureId;
    @Column(name = "speed_type")
    private String speedType;


    public Long getCreatureId() {
        return creatureId;
    }

    public void setCreatureId(Long creatureId) {
        this.creatureId = creatureId;
    }

    public String getSpeedType() {
        return speedType;
    }

    public void setSpeedType(String speedName) {
        this.speedType = speedName;
    }
}
