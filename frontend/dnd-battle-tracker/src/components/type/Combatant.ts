export type Combatant = {
  id: number;
  name: string;
  maxHp: number;
  currentHp: number;
  hitDice: string;
  armorClass: number;
  dexterity: number;
  initiative: number;
  groupId: number | null;
  statusEffects: string[]; // or another type if it's more complex
  templateCreatureId: number | null;
  combatantType: string;
  type: string;
};