import React, {useState} from "react";
import SearchSideBar from "../SearchSideBar/SearchSideBar";
import CombatantsTable from "../CombatantsTable/CombatantsTable";
import CreatureSheet from "../CreatureSheet/CreatureSheet";
import { Combatant } from "../type/Combatant";
import "./BattleTracker.css"

export default function BattleTracker() {
    const [combatants, setCombatants] = useState<Combatant[]>([]);
    const [selectedCombatant, setSelectedCombatant] = useState<Combatant | null>(null);

    const templateCreatureId = selectedCombatant?.templateCreatureId ?? null;
    console.log("Selected combatant templateCreatureId:", templateCreatureId);
    return (
        <>
            <SearchSideBar 
                combatants={combatants} 
                setCombatants={setCombatants} 
            />
            <CombatantsTable 
                combatants={combatants} 
                setCombatants={setCombatants}
                selectedCombatant={selectedCombatant}
                setSelectedCombatant={setSelectedCombatant}
            />
            <CreatureSheet 
                templateCreatureId={templateCreatureId} 
            />
        </>
    )
}