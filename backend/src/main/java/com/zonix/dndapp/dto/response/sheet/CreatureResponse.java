package com.zonix.dndapp.dto.response.sheet;

import com.zonix.dndapp.entity.TurnItemType;

import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class CreatureResponse {
    private long id;
    private String name;
    private String size;
    private String creatureType;
    private String alignment;
    private int maxHp;
    private int currentHp;
    private int armorClass;
    private TurnItemType type;
    private Map<String, Integer> abilities;
    private List<String> abilitySaves;
    private Set<String> skills;
    private Map<String, Integer> speeds;
    private Map<String, Map<String, String>> actions;
    private Map<String, Integer> savingThrows;
    private String languages;
    private int passivePerception;
    private String damageVulnerabilities;
    private String damageImmunities;
    private String damageResistances;
    private String ConditionImmunities;
    private String senses;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getArmorClass() {
        return armorClass;
    }

    public void setArmorClass(int armorClass) {
        this.armorClass = armorClass;
    }

    public TurnItemType getType() {
        return type;
    }

    public void setType(TurnItemType type) {
        this.type = type;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
    }

    public int getCurrentHp() {
        return currentHp;
    }

    public void setCurrentHp(int currentHp) {
        this.currentHp = currentHp;
    }

    public Map<String, Integer> getAbilities() {
        return abilities;
    }

    public void setAbilities(Map<String, Integer> abilities) {
        this.abilities = abilities;
    }

    public Set<String> getSkills() {
        return skills;
    }

    public void setSkills(Set<String> skills) {
        this.skills = skills;
    }

    public Map<String, Integer> getSpeeds() {
        return speeds;
    }

    public void setSpeeds(Map<String, Integer> speeds) {
        this.speeds = speeds;
    }

    public Map<String, Map<String, String>> getActions() {
        return actions;
    }

    public void setActions(Map<String, Map<String, String>> actions) {
        this.actions = actions;
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

    public List<String> getAbilitySaves() {
        return abilitySaves;
    }

    public void setAbilitySaves(List<String> abilitySaves) {
        this.abilitySaves = abilitySaves;
    }

    public Map<String, Integer> getSavingThrows() {
        return savingThrows;
    }

    public void setSavingThrows(Map<String, Integer> savingThrows) {
        this.savingThrows = savingThrows;
    }

    public String getLanguages() {
        return languages;
    }

    public void setLanguages(String languages) {
        this.languages = languages;
    }

    public int getPassivePerception() {
        return passivePerception;
    }

    public void setPassivePerception(int passivePerception) {
        this.passivePerception = passivePerception;
    }

    public String getDamageVulnerabilities() {
        return damageVulnerabilities;
    }

    public void setDamageVulnerabilities(String damageVulnerabilities) {
        this.damageVulnerabilities = damageVulnerabilities;
    }

    public String getDamageImmunities() {
        return damageImmunities;
    }

    public void setDamageImmunities(String damageImmunities) {
        this.damageImmunities = damageImmunities;
    }

    public String getDamageResistances() {
        return damageResistances;
    }

    public void setDamageResistances(String damageResistances) {
        this.damageResistances = damageResistances;
    }

    public String getConditionImmunities() {
        return ConditionImmunities;
    }

    public void setConditionImmunities(String conditionImmunities) {
        ConditionImmunities = conditionImmunities;
    }

    public String getSenses() {
        return senses;
    }

    public void setSenses(String senses) {
        this.senses = senses;
    }
}
