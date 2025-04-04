package com.zonix.dndapp.entity;


import com.zonix.dndapp.service.IdGeneratorService;

public class Combatant implements TurnQueueItem {
    private Long id;
    private String name;
    private Integer maxHp;
    private Integer currentHp;
    private Integer initiative;
    private Long groupId = null;
    private final TurnItemType combatantType = TurnItemType.INDIVIDUAL;

    public Combatant(TemplateCreature templateCreature) {
        this.id = IdGeneratorService.generateId();
        this.name = templateCreature.getName();
        this.maxHp = templateCreature.getHp();
        this.currentHp = templateCreature.getHp();
        this.initiative = templateCreature.getInitiative();
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
}
