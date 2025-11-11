package com.zonix.dndapp.dto.entity;

import com.zonix.dndapp.entity.TurnItemType;
import com.zonix.dndapp.entity.User;
import com.zonix.dndapp.entity.UserCombatant;

public class UserCombatantDTO {
    private Long id;
    private String name;
    private Integer maxHp;
    private Integer currentHp;
    private String hitDice;
    private Integer armorClass;
    private Integer dexterity;
    private Integer initiative;
    private Long templateCreatureId;
    private Long playerCharacterId;
    private String ownerUsername;
    private TurnItemType type;

    public UserCombatantDTO() {

    }

    public UserCombatantDTO(UserCombatant userCombatant) {
        this.id = userCombatant.getId();
        this.name = userCombatant.getName();
        this.maxHp = userCombatant.getMaxHp();
        this.currentHp = userCombatant.getCurrentHp();
        this.hitDice = userCombatant.getHitDice();
        this.armorClass = userCombatant.getArmorClass();
        this.dexterity = userCombatant.getDexterity();
        this.initiative = userCombatant.getInitiative();
        if (userCombatant.getTemplateCreature() != null) {
            this.templateCreatureId = userCombatant.getTemplateCreature().getId();
            this.type = TurnItemType.TEMPLATE_CREATURE;
        } else if (userCombatant.getPlayerCharacter() != null) {
            this.playerCharacterId = userCombatant.getPlayerCharacter().getId();
            this.type = TurnItemType.PLAYER_CHARACTER;
        }
        this.ownerUsername = userCombatant.getOwner().getUsername();
    }

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

    public Integer getMaxHp() {
        return maxHp;
    }

    public void setMaxHp(Integer maxHp) {
        this.maxHp = maxHp;
    }

    public Integer getCurrentHp() {
        return currentHp;
    }

    public void setCurrentHp(Integer currentHp) {
        this.currentHp = currentHp;
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

    public Integer getInitiative() {
        return initiative;
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

    public String getOwner() {
        return ownerUsername;
    }

    public void setOwner(String ownerUsername) {
        this.ownerUsername = ownerUsername;
    }

    public Long getPlayerCharacterId() {
        return playerCharacterId;
    }

    public void setPlayerCharacterId(Long playerCharacterId) {
        this.playerCharacterId = playerCharacterId;
    }

    public TurnItemType getType() {
        return type;
    }

    public void setType(TurnItemType type) {
        this.type = type;
    }
}
