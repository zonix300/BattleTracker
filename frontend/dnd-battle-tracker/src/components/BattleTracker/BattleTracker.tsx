import React, {useState} from "react";
import SearchSideBar from "../SearchSideBar/SearchSideBar";
import CombatantsTable from "../CombatantsTable/CombatantsTable";
import CreatureSheet from "../CreatureSheet/CreatureSheet";
import { Combatant } from "../type/Combatant";
import "./BattleTracker.css"
import { Combat } from "../type/Combat";

export default function BattleTracker() {
    const [combat, setCombat] = useState<Combat | undefined>();
    const [selectedCombatant, setSelectedCombatant] = useState<Combatant | null>(null);

    const templateCreatureId = selectedCombatant?.templateCreatureId ?? null;
    console.log("Selected combatant templateCreatureId:", templateCreatureId);
    return (
        <>
            <SearchSideBar 
                combat={combat} 
                setCombat={setCombat} 
            />
            <CombatantsTable 
                combat={combat} 
                setCombat={setCombat}
                selectedCombatant={selectedCombatant}
                setSelectedCombatant={setSelectedCombatant}
            />
            <CreatureSheet 
                templateCreatureId={templateCreatureId} 
            />
        </>
    )
}