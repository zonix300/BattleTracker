export type CreateCreatureRequest = {
  name: string;
  size: string;
  type: string;
  alignment: string;
  armorClass: number;
  armorDescription: string;
  hitPoints: number;
  hitDice: string;
  speeds: Record<string, string>;
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
  savingThrows: Record<string, boolean>;
  abilities: { name: string; description: string }[];
  actions: { name: string; description: string }[];
  bonusActions: { name: string; description: string }[];
  reactions: { name: string; description: string }[];
  legendaryDescription: string;
  legendaryActions: { name: string; description: string }[];
  description: string;
};