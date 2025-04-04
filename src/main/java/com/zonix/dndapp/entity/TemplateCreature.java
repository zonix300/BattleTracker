package com.zonix.dndapp.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "template_creatures")
public class TemplateCreature {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String size;
    private String creatureType;
    private String alignment;

    private int armorClass;
    private String hitPoints;
    private String speed;

    private int strength;
    private int dexterity;
    private int constitution;
    private int intelligence;
    private int wisdom;
    private int charisma;

    private String savingThrows;
    private String skills;

    private String damageResistances;
    private String damageImmunities;
    @Column(name = "condition_immunities")
    private String conditionImmunities;
    private String senses;
    private String languages;

    @Lob
    private String specialAbilities;

    @OneToMany(mappedBy = "creature", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CreatureAction> actions = new ArrayList<>();

    private double challengeRating;
    private Integer experiencePoints;

    public TemplateCreature() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getCreatureType() {
        return creatureType;
    }

    public void setCreatureType(String creatureType) {
        this.creatureType = creatureType;
    }

    public String getAlignment() {
        return alignment;
    }

    public void setAlignment(String alignment) {
        this.alignment = alignment;
    }

    public int getArmorClass() {
        return armorClass;
    }

    public void setArmorClass(int armorClass) {
        this.armorClass = armorClass;
    }

    public String getHitPoints() {
        return hitPoints;
    }

    public void setHitPoints(String hitPoints) {
        this.hitPoints = hitPoints;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getDexterity() {
        return dexterity;
    }

    public void setDexterity(int dexterity) {
        this.dexterity = dexterity;
    }

    public int getConstitution() {
        return constitution;
    }

    public void setConstitution(int constitution) {
        this.constitution = constitution;
    }

    public int getIntelligence() {
        return intelligence;
    }

    public void setIntelligence(int intelligence) {
        this.intelligence = intelligence;
    }

    public int getWisdom() {
        return wisdom;
    }

    public void setWisdom(int wisdom) {
        this.wisdom = wisdom;
    }

    public int getCharisma() {
        return charisma;
    }

    public void setCharisma(int charisma) {
        this.charisma = charisma;
    }

    public String getSavingThrows() {
        return savingThrows;
    }

    public void setSavingThrows(String savingThrows) {
        this.savingThrows = savingThrows;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getDamageResistances() {
        return damageResistances;
    }

    public void setDamageResistances(String damageResistance) {
        this.damageResistances = damageResistance;
    }

    public String getDamageImmunities() {
        return damageImmunities;
    }

    public void setDamageImmunities(String damageImmunities) {
        this.damageImmunities = damageImmunities;
    }

    public String getConditionImmunities() {
        return conditionImmunities;
    }

    public void setConditionImmunities(String conditionImmunities) {
        this.conditionImmunities = conditionImmunities;
    }

    public String getSenses() {
        return senses;
    }

    public void setSenses(String senses) {
        this.senses = senses;
    }

    public String getLanguages() {
        return languages;
    }

    public void setLanguages(String languages) {
        this.languages = languages;
    }

    public String getSpecialAbilities() {
        return specialAbilities;
    }

    public void setSpecialAbilities(String specialAbilities) {
        this.specialAbilities = specialAbilities;
    }

    public List<CreatureAction> getActions() {
        return actions;
    }

    public void setActions(List<CreatureAction> actions) {
        this.actions = actions;
    }

    public double getChallengeRating() {
        return challengeRating;
    }

    public void setChallengeRating(double challengeRatings) {
        this.challengeRating = challengeRatings;
    }

    public int getExperiencePoints() {
        return experiencePoints;
    }

    public void setExperiencePoints(int experiencePoints) {
        this.experiencePoints = experiencePoints;
    }
}
