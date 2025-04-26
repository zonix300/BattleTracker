package com.zonix.dndapp.entity;


import com.zonix.dndapp.service.IdGeneratorService;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Combatant implements TurnQueueItem {
    private Long id;
    private String name;
    private Integer maxHp;
    private Integer currentHp;
    private Integer initiative;
    private Long groupId = null;
    private Set<StatusEffect> statusEffects = EnumSet.noneOf(StatusEffect.class);
    private final TurnItemType combatantType = TurnItemType.INDIVIDUAL;
    private final TemplateCreature templateCreature;

    public Combatant(TemplateCreature templateCreature) {
        this.id = IdGeneratorService.generateId();
        this.name = templateCreature.getName();
        this.maxHp = rollHitPoints(templateCreature.getHitPoints());
        this.currentHp = this.maxHp;
        this.initiative = rollInitiative() + getDexterityModifier(templateCreature.getDexterity());
        this.templateCreature = templateCreature;
    }

    private Integer rollHitPoints(String inputHPString) {
        if (inputHPString == null || inputHPString.isEmpty()){
            return null;
        }

        try {
            int start = inputHPString.indexOf('(');
            int end = inputHPString.indexOf(')');

            if (start == -1 || end == -1 || start >= end) {
                return null;
            }

            String expression = inputHPString.substring(start + 1, end).trim();

            Pattern pattern = Pattern.compile("^(\\d+)d(\\d+)([+-]\\d+)?$");
            Matcher matcher = pattern.matcher(expression);

            if (!matcher.matches()) {
                return null;
            }

            int numberOfDice = Integer.parseInt(matcher.group(1));
            int diceSides = Integer.parseInt(matcher.group(2));
            int modifier = 0;
            if (matcher.group(3) != null) {
                modifier = Integer.parseInt(matcher.group(3));  // e.g., +1 or -3
            }



            Random random = new Random();
            int total = 0;
            for (int i = 0; i < numberOfDice; i++) {
                total += random.nextInt(diceSides) + 1;
            }

            return total + modifier;

        } catch (NumberFormatException | StringIndexOutOfBoundsException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Integer parseHitPoints() {
        return 0;
    }

    private Integer getDexterityModifier(Integer dexterity) {
        return (dexterity - 10)/2;
    }

    private Integer rollInitiative() {
        Random random = new Random();
        return random.nextInt(20) + 1;
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

    public TemplateCreature getTemplateCreature() {
        return templateCreature;
    }
}
