export type Speed = {
  id: {
    creatureId: number;
    speedType: string;
  }
  value: number;
  name: string;
}

type Ability = {
  id: number;
  name: string;
  description: string;
}

type Action = {
  id: number;
  name: string;
  description: string;
  attackBonus: string;
  damageDice: string;
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
  armorClass: number;
  armorDescription: string;
  hitPoints: number;
  hitDice: string;
  speeds: Speed[];
  skills: Record<string, boolean>;
  damageResistances: string[];
  damageVulnerabilities: string[];
  damageImmunities: string[];
  conditionImmunities: string[];
  senses: { name: string; value: string }[];
  languages: string[];
  challengeRating: string;
  strength: number;
  dexterity: number;
  constitution: number;
  intelligence: number;
  wisdom: number;
  charisma: number;
  strengthSave: number;
  dexteritySave: number;
  constitutionSave: number;
  intelligenceSave: number;
  wisdomSave: number;
  charismaSave: number;
  abilities: Ability[];
  actions: Action[];
  bonusActions: BonusAction[];
  reactions: Reaction[];
  legendaryDescription: string;
  legendaryActions: LegendaryAction[];
  description: string;
};