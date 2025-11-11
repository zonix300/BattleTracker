import { Speed } from "../components/PCArmorClassSpeed"
import { classInfo } from "../components/PCClassData"
import { HPData } from "./HPData"
import { Ability, Skill } from "./PCCLasses"

export type AttackType = 
    | "melee"
    | "ranged"
    | "spell";

export type Attack = {
    name: string;
    type: AttackType;
    actionType: ActionType;
    linkedWeapon: string;
    range: number;
    hit_dc: number;
    damage: Damage;
};

type Damage = {
    type: DamageType;
    ability: Ability;
    otherBonuses: number;
    diceNumber: number;
    diceType: number;
};

type Mastery = {
    name: string,
    items: string,
    description: string
};

export type Effect = {
  name: string;
  applyEffectTo: "attack" | "damage" | "saving_throw" | "ability_check" | "skill" | "initiative";
  diceValue: string;
  otherBonus: number;
  description: string;
};

export type Action = {
  name: string;
  description: string;
  type: ActionType;
};

type Item = {
    name: string,
    quantity: number,
    weight: number,
    description: string
    type: "armor" | "tool" | "weapon" | "heal"
}

type FeatureAndTraits = {
    name: string,
    actionType: ActionType,
    description: string,
    sourceType: "species" | "background" | "class" | "feat" | "item" | "spell" | "custom",
}

export type SkillData = {
    ability: Ability,
    baseValue: number,
    proficiency: boolean,
    otherBonuses: number,
    total: number
}

export type DamageType = 
    | "acid" 
    | "bludgeoning" 
    | "cold" 
    | "fire" 
    | "force" 
    | "lightning" 
    | "necrotic" 
    | "piercing" 
    | "poison" 
    | "psychic" 
    | "radiant" 
    | "slashing" 
    | "thunder";

export type ActionType = 
    | "action"
    | "bonusAction"
    | "reaction"
    | "free_action";

export type PlayerCharacter = {
    id?: number,
    name: string,
    level: number,
    proficiencyBonus: number,
    experience: number,
    clazz: keyof typeof classInfo,
    species: string,
    abilities: Record<Ability, number>,
    skills: Record<Skill, SkillData>,
    chosenProficiencies: Skill[],
    hp: HPData,
    armorClass: number,
    speed: Speed,
    combat: {
        attacks: Attack[],
        masteries: Mastery[],
        effects: Effect[],
        actions: Action[]
    },
    spells: {

    }
    inventory: {
        coins: {
            platinum: number,
            gold: number,
            electrum: number,
            silver: number,
            copper: number
        },
        totalWeight: number,
        equipment: Item[]
    },
    featuresTraits: FeatureAndTraits[]
    notes: {
        name: string,
        category: string,
        notes: string
    }[],
    about: {
        background: {
            name: string,
            description: string,
        }[],
        characteristics: {
            alignment: string,
            gender: string,
            eyeColor: string,
            height: number,
            faith: string,
            hairColor: string,
            skinColor: string,
            age: number,
            size: "tiny" | "small" | "medium" | "large" | "huge" | "gargantuan"
        },
        appearance: {
            name: string,
            description: string
        }[],
    },
    defenses: {
        resistances: string,
        vulnerabilities: string,
        immunities: string
    }
    conditions: String[],
    senses: {
        passive: {
            investigation: number,
            insight: number,
            perception: number
        },
        active: {
            darkvision: number,
            blindsight: number,
            truesight: number,
            tremorsense: number
        }
    },
    proficiencies: {
        category: "tool" | "weapon" | "armor",
        name: string,
        level: "half_proficient" | "proficient" | "expertise"
    }[],
    languages: string | undefined,
}