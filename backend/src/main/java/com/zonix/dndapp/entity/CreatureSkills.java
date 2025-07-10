package com.zonix.dndapp.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "creature_skills")
public class CreatureSkills {
    @EmbeddedId
    private CreatureSkillsId id;

    @Column(name = "skill_value")
    private Integer value;

    @ManyToOne
    @MapsId("creatureId")
    @JoinColumn(name = "creature_id")
    private TemplateCreature creature;

    public CreatureSkillsId getId() {
        return id;
    }

    public void setId(CreatureSkillsId id) {
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

    public void setName(String name) {
        id.setSkillName(name);
    }

    public String getName() {
        return id.getSkillName();
    }

    @Override
    public String toString() {
        return "CreatureSkills{" +
                "creatureId=" + id.getCreatureId() +
                ", skill name=" + id.getSkillName() +
                ", skill value=" + value +
                ", creature=" + creature +
                '}';
    }
}
