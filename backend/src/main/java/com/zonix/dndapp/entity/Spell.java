package com.zonix.dndapp.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "spells")
public class Spell {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String description;
    private String higherLevel;
    private String range;
    private Integer targetRange;

    private String components;
    private Boolean requiresVerbalComponent;
    private Boolean requiresSomaticComponent;
    private Boolean requiresMaterialComponent;
    private String material;

    private Boolean canBeCastAsRitual;
    private String ritual;

    private String duration;

    private String concentration;
    private Boolean requiresConcentration;

    private String castingTime;

    private String levelStr;
    private Integer levelInt;

    private String school;
    private String archetype;
    private String circles;
    private String documentTitle;

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

    public String getHigherLevel() {
        return higherLevel;
    }

    public void setHigherLevel(String higherLevel) {
        this.higherLevel = higherLevel;
    }

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }

    public Integer getTargetRange() {
        return targetRange;
    }

    public void setTargetRange(Integer targetRange) {
        this.targetRange = targetRange;
    }

    public String getComponents() {
        return components;
    }

    public void setComponents(String components) {
        this.components = components;
    }

    public Boolean getRequiresVerbalComponent() {
        return requiresVerbalComponent;
    }

    public void setRequiresVerbalComponent(Boolean requiresVerbalComponent) {
        this.requiresVerbalComponent = requiresVerbalComponent;
    }

    public Boolean getRequiresSomaticComponent() {
        return requiresSomaticComponent;
    }

    public void setRequiresSomaticComponent(Boolean requiresSomaticComponent) {
        this.requiresSomaticComponent = requiresSomaticComponent;
    }

    public Boolean getRequiresMaterialComponent() {
        return requiresMaterialComponent;
    }

    public void setRequiresMaterialComponent(Boolean requiresMaterialComponent) {
        this.requiresMaterialComponent = requiresMaterialComponent;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public Boolean getCanBeCastAsRitual() {
        return canBeCastAsRitual;
    }

    public void setCanBeCastAsRitual(Boolean canBeCastAsRitual) {
        this.canBeCastAsRitual = canBeCastAsRitual;
    }

    public String getRitual() {
        return ritual;
    }

    public void setRitual(String ritual) {
        this.ritual = ritual;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getConcentration() {
        return concentration;
    }

    public void setConcentration(String concentration) {
        this.concentration = concentration;
    }

    public Boolean getRequiresConcentration() {
        return requiresConcentration;
    }

    public void setRequiresConcentration(Boolean requiresConcentration) {
        this.requiresConcentration = requiresConcentration;
    }

    public String getCastingTime() {
        return castingTime;
    }

    public void setCastingTime(String castingTime) {
        this.castingTime = castingTime;
    }

    public String getLevelStr() {
        return levelStr;
    }

    public void setLevelStr(String levelStr) {
        this.levelStr = levelStr;
    }

    public Integer getLevelInt() {
        return levelInt;
    }

    public void setLevelInt(Integer levelInt) {
        this.levelInt = levelInt;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getArchetype() {
        return archetype;
    }

    public void setArchetype(String archetype) {
        this.archetype = archetype;
    }

    public String getCircles() {
        return circles;
    }

    public void setCircles(String circles) {
        this.circles = circles;
    }

    public String getDocumentTitle() {
        return documentTitle;
    }

    public void setDocumentTitle(String documentTitle) {
        this.documentTitle = documentTitle;
    }
}
