export type Combatant = {
  id: number;
  name: string;
  maxHp: number;
  currentHp: number;
  hitDice: string;
  armorClass: string;
  dexterity: number;
  initiative: number;
  groupId: number | null;
  statusEffects: string[];
  templateCreatureId: number | null;
  combatantType: string;
  type: string;
};