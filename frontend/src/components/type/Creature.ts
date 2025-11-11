export type Speed = {
  value: number;
  name: string;
}

type Ability = {
  id: number;
  name: string;
  description: string;
}

type Action = {
  name: string;
  description: string;
}

type BonusAction = {
  id: number;
  name: string;
  description: string;
}

type Reaction = {
  id: number;
  name: string;
  description: string;
}

type LegendaryAction = {
  id: number;
  name: string;
  description: string;
}

export type Creature = {
  name: string;
  size: string;
  creatureType: string;
  alignment: string;
  clazz: string;
  level: number;
  proficiencyBonus: number;
  experience: number;
  armorClass: number;
  armorDescription: string;
  currentHp: number;
  maxHp: number;
  hitPoints: number;
  hitDice: string;
  speeds: {
    name: number
  };
  skills: Record<string, boolean>;
  damageResistances: string[];
  damageVulnerabilities: string[];
  damageImmunities: string[];
  conditionImmunities: string[];
  senses: string;
  languages: string[];
  challengeRating: string;
  abilities: Record<string, number>;
  savingThrows: {
    name: number
  };
  specialAbilities: Record<string, string>;
  actions: Record<string, Action>;
  bonusActions: BonusAction[];
  reactions: Reaction[];
  legendaryDescription: string;
  legendaryActions: LegendaryAction[];
  description: string;
  type: "PLAYER_CHARACTER" | "TEMPLATE_CREATURE";
};