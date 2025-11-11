import { PCClassData } from "../types/PCCLasses";

export const classInfo: Record<string, PCClassData> = {
  barbarian: {
    hitDie: 12,
    savingThrows: ["strength", "constitution"],
    skillProficiencies: [2, ["animal_handling", "athletics", "intimidation", "nature", "perception", "survival"]],
    weaponProficiencies: ["simple", "martial"],
    armorTraining: ["light", "medium", "shields"]
  },
  bard: {
    hitDie: 8,
    savingThrows: ["dexterity", "charisma"],
    skillProficiencies: [3, ["acrobatics", "animal_handling", "arcana", "athletics", "deception", "history", "insight", "intimidation", "investigation", "medicine", "nature", "perception", "performance", "persuasion", "religion", "sleight_of_hand", "stealth", "survival"]],
    weaponProficiencies: ["simple", "martial"],
    armorTraining: ["light"]
  },
  cleric: {
    hitDie: 8,
    savingThrows: ["wisdom", "charisma"],
    skillProficiencies: [2, ["history", "insight", "medicine", "persuasion", "religion"]],
    weaponProficiencies: ["simple"],
    armorTraining: ["light", "medium", "shields"]
  },
  druid: {
    hitDie: 8,
    savingThrows: ["intelligence", "wisdom"],
    skillProficiencies: [2, ["arcana", "animal_handling", "insight", "medicine", "nature", "perception", "religion", "survival"]],
    weaponProficiencies: ["simple"],
    armorTraining: ["light", "medium", "shields"]
  },
  fighter: {
    hitDie: 10,
    savingThrows: ["strength", "constitution"],
    skillProficiencies: [2, ["acrobatics", "animal_handling", "athletics", "history", "insight", "intimidation", "perception", "survival"]],
    weaponProficiencies: ["simple", "martial"],
    armorTraining: ["light", "medium", "heavy", "shields"]
  },
  monk: {
    hitDie: 8,
    savingThrows: ["strength", "dexterity"],
    skillProficiencies: [2, ["acrobatics", "athletics", "history", "insight", "religion", "stealth"]],
    weaponProficiencies: ["simple", "martial"],
    armorTraining: []
  },
  paladin: {
    hitDie: 10,
    savingThrows: ["wisdom", "charisma"],
    skillProficiencies: [2, ["athletics", "insight", "intimidation", "medicine", "persuasion", "religion"]],
    weaponProficiencies: ["simple", "martial"],
    armorTraining: ["light", "medium", "heavy", "shields"]
  },
  ranger: {
    hitDie: 10,
    savingThrows: ["strength", "constitution"],
    skillProficiencies: [3, ["animal_handling", "athletics", "insight", "investigation", "nature", "perception", "stealth", "survival"]],
    weaponProficiencies: ["simple", "martial"],
    armorTraining: ["light", "medium", "shields"]
  },
  rogue: {
    hitDie: 8,
    savingThrows: ["dexterity", "intelligence"],
    skillProficiencies: [4, ["acrobatics", "athletics", "deception", "insight", "intimidation", "investigation", "perception", "performance", "persuasion", "sleight_of_hand", "stealth"]],
    weaponProficiencies: ["simple", "martial"],
    armorTraining: ["light"]
  },
  sorcerer: {
    hitDie: 6,
    savingThrows: ["constitution", "charisma"],
    skillProficiencies: [2, ["arcana", "deception", "insight", "intimidation", "persuasion", "religion"]],
    weaponProficiencies: ["simple"],
    armorTraining: []
  },
  warlock: {
    hitDie: 8,
    savingThrows: ["wisdom", "charisma"],
    skillProficiencies: [2, ["arcana", "deception", "history", "intimidation", "investigation", "nature", "religion"]],
    weaponProficiencies: ["simple"],
    armorTraining: ["light"]
  },
  wizard: {
    hitDie: 6,
    savingThrows: ["intelligence", "wisdom"],
    skillProficiencies: [2, ["arcana", "history", "insight", "investigation", "medicine", "religion"]],
    weaponProficiencies: ["simple"],
    armorTraining: []
  }
};
