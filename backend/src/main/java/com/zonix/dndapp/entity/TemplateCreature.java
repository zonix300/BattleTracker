package com.zonix.dndapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zonix.dndapp.util.DndUtils;
import jakarta.persistence.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

@Entity
@Table(name = "creatures")
public class TemplateCreature {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String size;

    @Column(name = "type")
    private String creatureType;
    private String alignment;

    private Integer armorClass;
    private String armorDescription;
    private Integer hitPoints;
    private String hitDice;

    private int strength;
    private int dexterity;
    private int constitution;
    private int intelligence;
    private int wisdom;
    private int charisma;

    private Integer strengthSave;
    private Integer dexteritySave;
    private Integer constitutionSave;
    private Integer intelligenceSave;
    private Integer wisdomSave;
    private Integer charismaSave;

    private String damageResistances;
    private String damageVulnerabilities;
    private String damageImmunities;
    private String conditionImmunities;
    private String senses;
    private String languages;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "creature_actions",
            joinColumns = @JoinColumn(name = "creature_id"),
            inverseJoinColumns = @JoinColumn(name = "action_id")
    )
    @Fetch(FetchMode.SUBSELECT)
    private Set<Action> actions = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "creature_bonus_actions",
            joinColumns = @JoinColumn(name = "creature_id"),
            inverseJoinColumns = @JoinColumn(name = "bonus_action_id")
    )
    @Fetch(FetchMode.SUBSELECT)
    private Set<BonusAction> bonusActions = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "creature_special_abilities",
            joinColumns = @JoinColumn(name = "creature_id"),
            inverseJoinColumns = @JoinColumn(name = "special_ability_id")
    )
    @Fetch(FetchMode.SUBSELECT)
    private Set<Ability> abilities = new HashSet<>();

    @ElementCollection
    @CollectionTable(
            name = "creature_environments",
            joinColumns = @JoinColumn(name = "creature_id")
    )
    @Column(name = "environment_name")
    private Set<String> environments = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "creature_legendary_actions",
            joinColumns = @JoinColumn(name = "creature_id"),
            inverseJoinColumns = @JoinColumn(name = "legendary_action_id")
    )
    @Fetch(FetchMode.SUBSELECT)
    private Set<LegendaryAction> legendaryActions = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "creature_reactions",
            joinColumns = @JoinColumn(name = "creature_id"),
            inverseJoinColumns = @JoinColumn(name = "reaction_id")
    )
    @Fetch(FetchMode.SUBSELECT)
    private Set<Reaction> reactions = new HashSet<>();

    @JsonIgnoreProperties("creature")
    @OneToMany(mappedBy = "creature", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CreatureSpeed> speeds = new HashSet<>();

    @JsonIgnoreProperties("creature")
    @OneToMany(mappedBy = "creature", cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(FetchMode.SUBSELECT)
    private Set<CreatureSkills> skills = new HashSet<>();

    @Column(name = "challenge_rating")
    private String challengeRating;
    @Transient
    private Integer experiencePoints = DndUtils.getXpForCr(challengeRating);

    private String description;
    private String legendaryDescription;

    @Column(name = "made_by")
    private String documentTitle;

    public TemplateCreature() {
    }


    @Override
    public String toString() {
        return "TemplateCreature{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", size='" + size + '\'' +
                ", creatureType='" + creatureType + '\'' +
                ", alignment='" + alignment + '\'' +
                ", armorClass=" + armorClass +
                ", armorDescription='" + armorDescription + '\'' +
                ", hitPoints=" + hitPoints +
                ", hitDice='" + hitDice + '\'' +
                ", strength=" + strength +
                ", dexterity=" + dexterity +
                ", constitution=" + constitution +
                ", intelligence=" + intelligence +
                ", wisdom=" + wisdom +
                ", charisma=" + charisma +
                ", strengthSave=" + strengthSave +
                ", dexteritySave=" + dexteritySave +
                ", constitutionSave=" + constitutionSave +
                ", intelligenceSave=" + intelligenceSave +
                ", wisdomSave=" + wisdomSave +
                ", charismaSave=" + charismaSave +
                ", damageResistances='" + damageResistances + '\'' +
                ", damageVulnerabilities='" + damageVulnerabilities + '\'' +
                ", damageImmunities='" + damageImmunities + '\'' +
                ", conditionImmunities='" + conditionImmunities + '\'' +
                ", senses='" + senses + '\'' +
                ", languages='" + languages + '\'' +
                ", actions=" + actions +
                ", bonusActions=" + bonusActions +
                ", abilities=" + abilities +
                ", environments=" + environments +
                ", legendaryActions=" + legendaryActions +
                ", reactions=" + reactions +
                ", speeds=" + speeds +
                ", skills=" + skills +
                ", challengeRating='" + challengeRating + '\'' +
                ", experiencePoints=" + experiencePoints +
                ", description='" + description + '\'' +
                ", legendaryDescription='" + legendaryDescription + '\'' +
                ", documentTitle='" + documentTitle + '\'' +
                '}';
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

    public Integer getArmorClass() {
        return armorClass;
    }

    public void setArmorClass(Integer armorClass) {
        this.armorClass = armorClass;
    }

    public String getArmorDescription() {
        return armorDescription;
    }

    public void setArmorDescription(String armorDescription) {
        this.armorDescription = armorDescription;
    }

    public Integer getHitPoints() {
        return hitPoints;
    }

    public void setHitPoints(Integer hitPoints) {
        this.hitPoints = hitPoints;
    }

    public String getHitDice() {
        return hitDice;
    }

    public void setHitDice(String hitDice) {
        this.hitDice = hitDice;
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

    public Integer getStrengthSave() {
        return strengthSave;
    }

    public void setStrengthSave(Integer strengthSave) {
        this.strengthSave = strengthSave;
    }

    public Integer getDexteritySave() {
        return dexteritySave;
    }

    public void setDexteritySave(Integer dexteritySave) {
        this.dexteritySave = dexteritySave;
    }

    public Integer getConstitutionSave() {
        return constitutionSave;
    }

    public void setConstitutionSave(Integer constitutionSave) {
        this.constitutionSave = constitutionSave;
    }

    public Integer getIntelligenceSave() {
        return intelligenceSave;
    }

    public void setIntelligenceSave(Integer intelligenceSave) {
        this.intelligenceSave = intelligenceSave;
    }

    public Integer getWisdomSave() {
        return wisdomSave;
    }

    public void setWisdomSave(Integer wisdomSave) {
        this.wisdomSave = wisdomSave;
    }

    public Integer getCharismaSave() {
        return charismaSave;
    }

    public void setCharismaSave(Integer charismaSave) {
        this.charismaSave = charismaSave;
    }

    public String getDamageResistances() {
        return damageResistances;
    }

    public void setDamageResistances(String damageResistances) {
        this.damageResistances = damageResistances;
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

    public Set<Action> getActions() {
        return actions;
    }

    public void setActions(Set<Action> actions) {
        this.actions = actions;
    }

    public Set<BonusAction> getBonusActions() {
        return bonusActions;
    }

    public void setBonusActions(Set<BonusAction> bonusActions) {
        this.bonusActions = bonusActions;
    }

    public Set<Ability> getAbilities() {
        return abilities;
    }

    public void setAbilities(Set<Ability> abilities) {
        this.abilities = abilities;
    }

    public Set<String> getEnvironments() {
        return environments;
    }

    public void setEnvironments(Set<String> environments) {
        this.environments = environments;
    }

    public Set<LegendaryAction> getLegendaryActions() {
        return legendaryActions;
    }

    public void setLegendaryActions(Set<LegendaryAction> legendaryActions) {
        this.legendaryActions = legendaryActions;
    }

    public Set<Reaction> getReactions() {
        return reactions;
    }

    public void setReactions(Set<Reaction> reactions) {
        this.reactions = reactions;
    }

    public Set<CreatureSpeed> getSpeeds() {
        return speeds;
    }

    public void setSpeeds(Set<CreatureSpeed> speeds) {
        this.speeds = speeds;
    }

    public Set<CreatureSkills> getSkills() {
        return skills;
    }

    public void setSkills(Set<CreatureSkills> skills) {
        this.skills = skills;
    }

    public String getChallengeRating() {
        return challengeRating;
    }

    public void setChallengeRating(String challengeRating) {
        this.challengeRating = challengeRating;
    }

    public Integer getExperiencePoints() {
        return experiencePoints;
    }

    public void setExperiencePoints(Integer experiencePoints) {
        this.experiencePoints = experiencePoints;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLegendaryDescription() {
        return legendaryDescription;
    }

    public void setLegendaryDescription(String legendaryDescription) {
        this.legendaryDescription = legendaryDescription;
    }

    public String getDocumentTitle() {
        return documentTitle;
    }

    public void setDocumentTitle(String documentTitle) {
        this.documentTitle = documentTitle;
    }
}