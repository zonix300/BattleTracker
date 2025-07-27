import React, {useState} from "react";
import { Combatant } from "../type/Combatant";
import { useCombatActions } from "../../hooks/useCombatActions";
import { ReactComponent as SettingsIcon } from "../../icons/settings.svg";
import { ReactComponent as InitiativeIcon } from "../../icons/sand-clock.svg";
import { ReactComponent as WriteIcon } from "../../icons/write.svg";
import { ReactComponent as HeartIcon } from "../../icons/heart.svg";

import { ReactComponent as XmarkIcon } from "../../icons/xmark.svg"
import { ReactComponent as CheckIcon } from "../../icons/check.svg"
import { ReactComponent as DiceIcon } from "../../icons/dice.svg"
import { ReactComponent as ArrowIcon } from "../../icons/arrow.svg"
import { ReactComponent as TrashIcon } from "../../icons/trash.svg"

import "./CombatantsTable.css"
import { useCombatantChanges } from "../../hooks/useCombatantChanges";
import { Combat } from "../type/Combat";

export default function CombatantsTable({combat, setCombat, selectedCombatant, setSelectedCombatant} : {
  combat: Combat | undefined;
  setCombat: React.Dispatch<React.SetStateAction<Combat | undefined>>;
  selectedCombatant: Combatant | null;
  setSelectedCombatant: React.Dispatch<React.SetStateAction<Combatant | null>>;
}) {
  const handleCombatantClick = (combatant: Combatant) => {
    setSelectedCombatant(combatant);
    console.log("Selected combatant:", combatant);
  };

  const [changingId, setChangingId] = useState<number>();
  const [editedCombatant, setEditedCombatant] = useState<Combatant | null>(null);
  const [currentCombatantId, setCurrentCombatantId] = useState<number | undefined>(undefined);

  const {
    handleCombatantChanges,
    isCombatantChanging
  } = useCombatantChanges(setCombat, setChangingId, setEditedCombatant);

  const {
    handleInitiativeRoll,
    handleDamage,
    handleHeal,
    handleEffects,
    handleNextTurn,
    handlePageReload,
    handleDelete,
    isInitiativeRolling
  } = useCombatActions(setCombat, setEditedCombatant, setCurrentCombatantId);

  window.onload = () => {
    handlePageReload();
  }

  const handleSettingButtonClick = (combatant: Combatant) => {
    if (changingId === combatant.id) {
      setChangingId(undefined);
      setEditedCombatant(null);
    } else {
      setChangingId(combatant.id);
      setEditedCombatant({ ...combatant });
    }
  };

  const revertCombatantChanges = () => {
    setChangingId(undefined);
    setEditedCombatant(null);
  }


  return (
    <div className="combatants-container">
      <table className="combatants-table">
        <thead className="combatants-table-header">
          <tr>
            <th>Initiative</th>
            <th className="combatant-name-header-container">Name</th>
            <th className="combatant-hp-header-container">Hit Points</th>
            <th className="combatant-ac-header-container">Armor Class</th>
            <th><button onClick={handleNextTurn}><ArrowIcon className="arrow-icon"/></button></th>
          </tr>
        </thead>
        <tbody className="combatants-table-body">
          {combat?.userCombatants
            .map((combatant) => (
              <>
                <tr
                  key={combatant.id}
                  className={`combatant-row ${selectedCombatant?.id === combatant.id ? "selected" : ""} ${combat.current?.id === combatant.id ? "current" : ""}`}
                  onClick={() => handleCombatantClick(combatant)}
                >
                  {changingId === combatant.id ? (
                    <>
                      <td className="combatant-initiative-container">
                        <button 
                          type="button" 
                          onClick={(e) => {
                            e.stopPropagation(); 
                            handleInitiativeRoll(combatant.id)
                          }}
                        ><DiceIcon className="dice-icon"/></button>
                        <input 
                          className="initiative-input"
                          type="number"
                          value={editedCombatant?.initiative ?? ""}
                          onChange={(e) => 
                            setEditedCombatant((prev) => 
                              prev ? { ...prev, initiative: Number(e.target.value) } : prev
                            )
                          }
                          disabled={isInitiativeRolling(combatant.id)}
                        />
                      </td>
                      <td className="combatant-name-container">
                        <input 
                          className="name-input"
                          type="text"
                          value={editedCombatant?.name ?? combatant.name}
                          onChange={(e) => 
                            setEditedCombatant((prev) => 
                              prev ? { ...prev, name: e.target.value } : prev
                            )
                          }
                        />
                      </td>
                      <td className="combatant-hp-container">
                        <input 
                          className="current-hp-input"
                          type="number"
                          value={editedCombatant?.currentHp ?? combatant.currentHp}
                          onChange={(e) => 
                            setEditedCombatant((prev) => 
                              prev ? { ...prev, currentHp: Number(e.target.value) } : prev  
                            )
                          }
                        />
                        /
                        <input 
                          className="max-hp-input"
                          type="text"
                          value={editedCombatant?.maxHp ?? combatant.maxHp}
                          onChange={(e) => 
                            setEditedCombatant((prev) => 
                              prev ? { ...prev, maxHp: Number(e.target.value) } : prev
                            )
                          }
                        />
                      </td>
                      <td className="combatant-ac-container">
                        <input 
                          className="ac-input"
                          type="number"
                          value={editedCombatant?.armorClass ?? combatant.armorClass}
                          onChange={(e) =>
                            setEditedCombatant((prev) =>
                              prev ? { ...prev, armorClass: e.target.value } : prev
                            )
                          }
                        />
                      </td>
                      <td>
                        <button onClick={() => handleCombatantChanges(editedCombatant)}><CheckIcon className="check-icon"/></button>
                        <button onClick={() => revertCombatantChanges()}><XmarkIcon className="xmark-icon"/></button>
                      </td>
                    </>
                  ) : (
                    <>
                      <td className="combatant-initiative-container">{combatant.initiative}</td>
                      <td className="combatant-name-container">{combatant.name}</td>
                      <td className="combatant-hp-container">{combatant.currentHp}/{combatant.maxHp}</td>
                      <td className="combatant-ac-container">{combatant.armorClass}</td>
                      <td>
                        <button onClick={() => handleSettingButtonClick(combatant)}><SettingsIcon className="settings-icon"/></button>
                        <button onClick={() => handleDelete(combatant.id)}><TrashIcon className="trash-icon" /></button>
                      </td>
                    </>
                  )}
                  
                  
                </tr>
              </>
              ))}
        </tbody>
      </table>
    </div>
  );
};