export type Skill = 
    | "acrobatics"
    | "animal_handling"
    | "arcana"
    | "athletics"
    | "deception"
    | "history"
    | "insight"
    | "intimidation"
    | "investigation"
    | "medicine"
    | "nature"
    | "perception"
    | "performance"
    | "persuasion"
    | "religion"
    | "sleight_of_hand"
    | "stealth"
    | "survival";

export type Ability =
    | "strength"
    | "dexterity"
    | "constitution"
    | "intelligence"
    | "wisdom"
    | "charisma";

export type WeaponType =
    | "simple"
    | "martial";

export type ArmorTraining = 
    | "light"
    | "medium"
    | "heavy"
    | "shields";

export const SKILL_TO_ABILITY : Record<string, string> = {
        acrobatics : "dexterity",
        animal_handling : "wisdom",
        arcana : "intelligence",
        athletics : "strength",
        deception : "charisma",
        history : "intelligence",
        insight : "wisdom",
        intimidation : "charisma",
        investigation : "intelligence",
        medicine : "wisdom",
        nature : "intelligence",
        perception : "wisdom",
        performance : "charisma",
        persuasion : "charisma",
        religion : "intelligence",
        sleight_of_hand : "dexterity",
        stealth : "dexterity",
        survival : "wisdom"
    };




export type PCClassData = {
    hitDie: number,
    savingThrows: [Ability, Ability],
    skillProficiencies: [number, Skill[]],
    weaponProficiencies: WeaponType[],
    armorTraining: ArmorTraining[]
}