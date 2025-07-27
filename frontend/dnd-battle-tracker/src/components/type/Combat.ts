import { Combatant } from "./Combatant";

export type Combat = {
    id: number;
    name: string;
    current: Combatant;
    round: number;
    userCombatants: Combatant[];
}