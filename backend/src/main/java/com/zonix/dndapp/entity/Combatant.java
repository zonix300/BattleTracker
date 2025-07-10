package com.zonix.dndapp.entity;


import com.zonix.dndapp.service.IdGeneratorService;
import com.zonix.dndapp.util.DndUtils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Combatant implements TurnQueueItem {
    private Long id;
    private String name;
    private Integer maxHp;
    private Integer currentHp;
    private String hitDice;
    private Integer armorClass;
    private Integer dexterity;
    private Integer initiative;
    private Long groupId = null;
    private Set<StatusEffect> statusEffects = EnumSet.noneOf(StatusEffect.class);
    private Long templateCreatureId;
    private final TurnItemType combatantType = TurnItemType.INDIVIDUAL;

    public Combatant() {

    }

    public Combatant(Long id, String name, String hitDice, Integer armorClass, Integer dexterity) {
        this.id = IdGeneratorService.generateId();
        this.name = name;
        this.hitDice = hitDice;
        this.maxHp = DndUtils.roll(hitDice);
        this.currentHp = this.maxHp;
        this.armorClass = armorClass;
        this.dexterity = dexterity;
        this.initiative = DndUtils.roll(20) + DndUtils.calculateModifier(dexterity);
        this.templateCreatureId = id;
    }

    public Combatant(TemplateCreature templateCreature) {
        this.id = IdGeneratorService.generateId();
        this.name = templateCreature.getName();
        this.hitDice = templateCreature.getHitDice();
        this.maxHp = DndUtils.roll(hitDice);

    }

    public void rerollInitiative() {
        this.initiative = DndUtils.roll(20) + DndUtils.calculateModifier(initiative);
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Integer getInitiative() {
        return initiative;
    }

    @Override
    public TurnItemType getType() {
        return combatantType;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Integer getCurrentHp() {
        return currentHp;
    }

    public void setCurrentHp(Integer currentHp) {
        this.currentHp = currentHp;
    }

    public Integer getMaxHp() {
        return maxHp;
    }

    public void setMaxHp(Integer maxHp) {
        this.maxHp = maxHp;
    }

    public Set<StatusEffect> getStatusEffects() {
        return statusEffects;
    }

    public void setStatusEffects(Set<StatusEffect> statusEffects) {
        this.statusEffects = statusEffects;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHitDice() {
        return hitDice;
    }

    public void setHitDice(String hitDice) {
        this.hitDice = hitDice;
    }

    public Integer getArmorClass() {
        return armorClass;
    }

    public void setArmorClass(Integer armorClass) {
        this.armorClass = armorClass;
    }

    public Integer getDexterity() {
        return dexterity;
    }

    public void setDexterity(Integer dexterity) {
        this.dexterity = dexterity;
    }

    public void setInitiative(Integer initiative) {
        this.initiative = initiative;
    }

    public Long getTemplateCreatureId() {
        return templateCreatureId;
    }

    public void setTemplateCreatureId(Long templateCreatureId) {
        this.templateCreatureId = templateCreatureId;
    }

    public TurnItemType getCombatantType() {
        return combatantType;
    }
}
