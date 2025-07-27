package com.zonix.dndapp.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "user_combatants")
public class UserCombatant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User owner;

    private String name;
    private Integer maxHp;
    private Integer currentHp;
    private String hitDice;
    private Integer armorClass;
    private Integer dexterity;
    private Integer initiative;

    @ManyToOne
    @JoinColumn(name = "template_creature_id")
    private TemplateCreature templateCreature;

    @ManyToOne
    @JoinColumn(name = "battle_id")
    private Battle battle;

    public UserCombatant() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User user) {
        this.owner = user;
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
        if (this.currentHp == null) return;
        this.currentHp = Math.min(maxHp, currentHp);
    }

    public Integer getCurrentHp() {
        return currentHp;
    }

    public void setCurrentHp(Integer currentHp) {
        this.currentHp = Math.min(maxHp, currentHp);
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

    public TemplateCreature getTemplateCreature() {
        return templateCreature;
    }

    public void setTemplateCreature(TemplateCreature templateCreature) {
        this.templateCreature = templateCreature;
    }

    public Battle getBattle() {
        return battle;
    }

    public void setBattle(Battle battle) {
        this.battle = battle;
    }
}
