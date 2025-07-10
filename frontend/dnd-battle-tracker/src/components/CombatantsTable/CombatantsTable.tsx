import React, {useState} from "react";
import { Combatant } from "../type/Combatant";
import { useCombatActions } from "../../hooks/useCombatActions";
const API_BASE_URL = process.env.REACT_APP_API_BASE_URL

export default function CombatantsTable({combatants, setCombatants, selectedCombatant, setSelectedCombatant} : {
  combatants: Combatant[];
  setCombatants: React.Dispatch<React.SetStateAction<Combatant[]>>;
  selectedCombatant: Combatant | null;
  setSelectedCombatant: React.Dispatch<React.SetStateAction<Combatant | null>>;
}) {
  const handleCombatantClick = (combatant: Combatant) => {
    setSelectedCombatant(combatant);
    console.log("Selected combatant:", combatant);
  };

  const {
    handleInitiativeRoll,
    handleInitiativeChange,
    isRollingInitiative,
    isChangingInitiative
  } = useCombatActions(setCombatants);


  return (
    <div className="combatants-container">
      <table className="combatants-table">
        <thead className="combatants-table-header">
          <tr>
            <th>Initiative</th>
            <th>Name</th>
            <th>Hp</th>
            <th>Armor Class</th>
          </tr>
        </thead>
        <tbody className="combatants-table-body">
          {combatants
            .sort((a, b) => b.initiative - a.initiative)
            .map((combatant) => (
              <tr
                key={combatant.id}
                className={`combatant-row ${selectedCombatant?.id === combatant.id ? "selected" : ""}`}
                onClick={() => handleCombatantClick(combatant)}
              >
                <td>
                  <button onClick={() => handleInitiativeRoll(combatant.id)}
                          disabled={isRollingInitiative(combatant.id)}>🎲</button>
                  <input 
                    type="number"
                    value={combatant.initiative}
                    onChange={(e) => handleInitiativeChange(combatant.id, parseInt(e.target.value))}
                    disabled={isChangingInitiative(combatant.id)}
                  />
                  
                </td>
                <td>{combatant.name}</td>
                <td>{combatant.currentHp}/{combatant.maxHp}</td>
                <td>{combatant.armorClass}</td>
              </tr>
              ))}
        </tbody>
      </table>
    </div>
  );
};