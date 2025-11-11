package com.zonix.dndapp.entity.playerCharacter;

import com.zonix.dndapp.dto.entity.PlayerCharacterDTO;
import com.zonix.dndapp.entity.User;
import com.zonix.dndapp.entity.playerCharacter.helperType.Combat;
import com.zonix.dndapp.entity.playerCharacter.helperType.Inventory;
import com.zonix.dndapp.entity.playerCharacter.helperType.Sense;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;
import java.util.Map;

@Entity
@Table(name = "player_characters")
public class PlayerCharacter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int level;
    private int proficiencyBonus;
    private int experience;
    private String species;
    private String alignment;

    @Column(name = "class")
    private String clazz;

    private int armorClass;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    private User owner;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private Map<String, Integer> abilities;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private Map<String, Object> skills;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private Map<String, Integer> hp;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private Map<String, Integer> speed;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private Combat combat;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private Inventory inventory;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private List<Map<String, Object>> featuresTraits;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private List<Map<String, Object>> notes;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private Map<String, Object> about;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private Map<String, Map<String, Integer>> senses;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private List<Map<String, Object>> proficiencies;

    private String languages;

    private boolean active;

    public void editFromDTO(PlayerCharacterDTO dto) {
        this.name = dto.name();
        this.level = dto.level();
        this.proficiencyBonus = dto.proficiencyBonus();
        this.experience = dto.experience();
        this.species = dto.species();
        this.clazz = dto.clazz();
        this.armorClass = dto.armorClass();
        this.abilities = dto.abilities();
        this.skills = dto.skills();
        this.hp = dto.hp();
        this.speed = dto.speed();
        this.combat = dto.combat();
        this.inventory = dto.inventory();
        this.featuresTraits = dto.featuresTraits();
        this.notes = dto.notes();
        this.about = dto.about();
        this.senses = dto.senses();
        this.proficiencies = dto.proficiencies();
        this.languages = dto.languages();
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

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getProficiencyBonus() {
        return proficiencyBonus;
    }

    public void setProficiencyBonus(int proficiencyBonus) {
        this.proficiencyBonus = proficiencyBonus;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public int getArmorClass() {
        return armorClass;
    }

    public void setArmorClass(int armorClass) {
        this.armorClass = armorClass;
    }

    public Map<String, Integer> getAbilities() {
        return abilities;
    }

    public void setAbilities(Map<String, Integer> abilities) {
        this.abilities = abilities;
    }

    public Map<String, Object> getSkills() {
        return skills;
    }

    public void setSkills(Map<String, Object> skills) {
        this.skills = skills;
    }

    public Map<String, Integer> getHp() {
        return hp;
    }

    public void setHp(Map<String, Integer> hp) {
        this.hp = hp;
    }

    public Map<String, Integer> getSpeed() {
        return speed;
    }

    public void setSpeed(Map<String, Integer> speed) {
        this.speed = speed;
    }

    public Combat getCombat() {
        return combat;
    }

    public void setCombat(Combat combat) {
        this.combat = combat;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public List<Map<String, Object>> getFeaturesTraits() {
        return featuresTraits;
    }

    public void setFeaturesTraits(List<Map<String, Object>> featuresTraits) {
        this.featuresTraits = featuresTraits;
    }

    public List<Map<String, Object>> getNotes() {
        return notes;
    }

    public void setNotes(List<Map<String, Object>> notes) {
        this.notes = notes;
    }

    public Map<String, Object> getAbout() {
        return about;
    }

    public void setAbout(Map<String, Object> about) {
        this.about = about;
    }

    public Map<String, Map<String, Integer>> getSenses() {
        return senses;
    }

    public void setSenses(Map<String, Map<String, Integer>> senses) {
        this.senses = senses;
    }

    public List<Map<String, Object>> getProficiencies() {
        return proficiencies;
    }

    public void setProficiencies(List<Map<String, Object>> proficiencies) {
        this.proficiencies = proficiencies;
    }

    public String getLanguages() {
        return languages;
    }

    public void setLanguages(String  languages) {
        this.languages = languages;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getAlignment() {
        return alignment;
    }

    public void setAlignment(String alignment) {
        this.alignment = alignment;
    }

    public static class Builder {

        private final PlayerCharacter pc = new PlayerCharacter();

        private void initDefaultSkills() {
            pc.skills = Map.ofEntries(
                    Map.entry("acrobatics", Map.of(
                            "ability", "dexterity",
                            "baseValue", 0,
                            "proficiency", false,
                            "otherBonuses", 0,
                            "total", 0
                    )),
                    Map.entry("animal_handling", Map.of(
                            "ability", "wisdom",
                            "baseValue", 0,
                            "proficiency", false,
                            "otherBonuses", 0,
                            "total", 0
                    )),
                    Map.entry("arcana", Map.of(
                            "ability", "intelligence",
                            "baseValue", 0,
                            "proficiency", false,
                            "otherBonuses", 0,
                            "total", 0
                    )),
                    Map.entry("athletics", Map.of(
                            "ability", "strength",
                            "baseValue", 0,
                            "proficiency", false,
                            "otherBonuses", 0,
                            "total", 0
                    )),
                    Map.entry("deception", Map.of(
                            "ability", "charisma",
                            "baseValue", 0,
                            "proficiency", false,
                            "otherBonuses", 0,
                            "total", 0
                    )),
                    Map.entry("history", Map.of(
                            "ability", "intelligence",
                            "baseValue", 0,
                            "proficiency", false,
                            "otherBonuses", 0,
                            "total", 0
                    )),
                    Map.entry("insight", Map.of(
                            "ability", "wisdom",
                            "baseValue", 0,
                            "proficiency", false,
                            "otherBonuses", 0,
                            "total", 0
                    )),
                    Map.entry("intimidation", Map.of(
                            "ability", "charisma",
                            "baseValue", 0,
                            "proficiency", false,
                            "otherBonuses", 0,
                            "total", 0
                    )),
                    Map.entry("investigation", Map.of(
                            "ability", "intelligence",
                            "baseValue", 0,
                            "proficiency", false,
                            "otherBonuses", 0,
                            "total", 0
                    )),
                    Map.entry("medicine", Map.of(
                            "ability", "wisdom",
                            "baseValue", 0,
                            "proficiency", false,
                            "otherBonuses", 0,
                            "total", 0
                    )),
                    Map.entry("nature", Map.of(
                            "ability", "intelligence",
                            "baseValue", 0,
                            "proficiency", false,
                            "otherBonuses", 0,
                            "total", 0
                    )),
                    Map.entry("perception", Map.of(
                            "ability", "wisdom",
                            "baseValue", 0,
                            "proficiency", false,
                            "otherBonuses", 0,
                            "total", 0
                    )),
                    Map.entry("performance", Map.of(
                            "ability", "charisma",
                            "baseValue", 0,
                            "proficiency", false,
                            "otherBonuses", 0,
                            "total", 0
                    )),
                    Map.entry("persuasion", Map.of(
                            "ability", "charisma",
                            "baseValue", 0,
                            "proficiency", false,
                            "otherBonuses", 0,
                            "total", 0
                    )),
                    Map.entry("religion", Map.of(
                            "ability", "intelligence",
                            "baseValue", 0,
                            "proficiency", false,
                            "otherBonuses", 0,
                            "total", 0
                    )),
                    Map.entry("sleight_of_hand", Map.of(
                            "ability", "dexterity",
                            "baseValue", 0,
                            "proficiency", false,
                            "otherBonuses", 0,
                            "total", 0
                    )),
                    Map.entry("stealth", Map.of(
                            "ability", "dexterity",
                            "baseValue", 0,
                            "proficiency", false,
                            "otherBonuses", 0,
                            "total", 0
                    )),
                    Map.entry("survival", Map.of(
                            "ability", "wisdom",
                            "baseValue", 0,
                            "proficiency", false,
                            "otherBonuses", 0,
                            "total", 0
                    ))
            );
        }

        public Map<String, Map<String, Integer>> emptySenses() {
            Map<String, Integer> passiveSenses = Map.of(
                    "investigation", 0,
                    "insight", 0,
                    "perception", 0
            );

            Map<String, Integer> activeSenses = Map.of(
                    "darkvision", 0,
                    "blindsight", 0,
                    "truesight", 0,
                    "tremorsense", 0
            );

            return Map.of(
                    "active", activeSenses,
                    "passive", passiveSenses
            );
        }

        public Builder createDefault() {
            initDefaultSkills();
            pc.name = "Unnamed";
            pc.level = 1;
            pc.proficiencyBonus = 2;
            pc.experience = 0;
            pc.species = "human";
            pc.clazz = "fighter";
            pc.armorClass = 0;
            pc.abilities = Map.of(
                    "strength", 10,
                    "dexterity", 10,
                    "constitution", 10,
                    "intelligence", 10,
                    "wisdom", 10,
                    "charisma", 10
            );
            pc.hp = Map.of(
                    "max", 0,
                    "current", 0,
                    "temporary", 0
            );
            pc.speed = Map.of(
                "walk", 30,
                "fly", 0,
                "climb", 15,
                "swim", 30
            );
            pc.combat = Combat.emptyCombat();
            pc.alignment = "no alignment";
            pc.inventory = Inventory.emptyInventory();
            pc.senses = emptySenses();
            pc.active = false;
            return this;
        }

        public Builder owner(User owner) {
            pc.owner = owner;
            return this;
        }

        public Builder fromDTO(PlayerCharacterDTO dto) {
            pc.name = dto.name();
            pc.level = dto.level();
            pc.proficiencyBonus = dto.proficiencyBonus();
            pc.experience = dto.experience();
            pc.species = dto.species();
            pc.clazz = dto.clazz();
            pc.abilities = dto.abilities();
            pc.skills = dto.skills();
            pc.hp = dto.hp();
            pc.speed = dto.speed();
            pc.combat = dto.combat();
            pc.inventory = dto.inventory();
            pc.featuresTraits = dto.featuresTraits();
            pc.notes = dto.notes();
            pc.about = dto.about();
            pc.senses = dto.senses();
            pc.proficiencies = dto.proficiencies();
            pc.languages = dto.languages();
            pc.active = false;
            return this;
        }

        public PlayerCharacter build() {
            return pc;
        }
    }

}
