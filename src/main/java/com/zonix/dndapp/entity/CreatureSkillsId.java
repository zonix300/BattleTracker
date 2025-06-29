package com.zonix.dndapp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class CreatureSkillsId implements Serializable {

    @Column(name = "creature_id")
    private Long creatureId;

    @Column(name = "skill_name")
    private String skillName;


    public Long getCreatureId() {
        return creatureId;
    }

    public void setCreatureId(Long creatureId) {
        this.creatureId = creatureId;
    }

    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }
}
