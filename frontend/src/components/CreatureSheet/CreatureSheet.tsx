import {useState, useEffect} from "react";
import { Creature } from "../type/Creature";
import { apiClient } from "../../services/api";
import { useSearchCreature } from "../../hooks/useSeachCreature";
import "./CreatureSheet.css"
import { Combatant } from "../type/Combatant";
import { calcModifier } from "../dndUtils";

const API_BASE_URL = process.env.REACT_APP_API_BASE_URL

interface CreatureSheetProps {
    selectedCombatant: Combatant | null;
    isSelectedCreatureLocked: boolean,
    setIsSelectedCreatureLocked: React.Dispatch<React.SetStateAction<boolean>>
}

export default function CreatureSheet({ selectedCombatant, isSelectedCreatureLocked, setIsSelectedCreatureLocked }: CreatureSheetProps) {
    
    const [creature, setCreature] = useState<Creature | null>(null);

    const {
        handleSelectedCreatureChange,
        isSelectedCreatureChanging
    } = useSearchCreature(selectedCombatant, setCreature);


    useEffect(() => {
        console.log("Selected Combatant:", selectedCombatant);
        if (selectedCombatant) {
            handleSelectedCreatureChange();
        }
        
    }, [selectedCombatant]);

    const onLockButtonClick = () => {
        setIsSelectedCreatureLocked(!isSelectedCreatureLocked)
    }

    return (
        <div className="creature-sheet-container">
            {creature ? (
                <div className="creature-sheet">
                    <div className="creature-sheet-main">
                        <div className="creature-sheet-header">
                            <span>{creature.name}</span>
                            <button
                                onClick={onLockButtonClick}
                            >{isSelectedCreatureLocked ? "Unlock" : "Lock"}</button>
                        </div> 
                        <p className="creature-sheet-subheader">{creature.size} {creature.creatureType}, {creature.alignment && creature.alignment.length > 0 ? creature.alignment : "unaligned"}</p>
                        {creature.type === "PLAYER_CHARACTER" ? (
                            <>
                                <div className="creature-sheet-underscore-container">
                                    <div className="creature-sheet-underscore"></div>
                                </div>
                                <p className="creature-sheet-level"><strong>Level </strong>{creature.level} {creature.clazz}</p>
                                <p className="creature-sheet-pb"><strong>Proficiency Bonus </strong>{creature.proficiencyBonus >= 0 ? "+" + creature.proficiencyBonus : creature.proficiencyBonus}</p>
                                <p className="creature-sheet-exp"><strong>Experience </strong>{creature.experience}</p>

                            </>
                            ) : null
                        }

                        <div className="creature-sheet-underscore-container">
                            <div className="creature-sheet-underscore"></div>
                        </div>
                        <p className="creature-sheet-ac"><strong>Armor Class</strong> {creature.armorClass} {creature.armorDescription ? (creature.armorDescription) : ""}</p>
                        <p className="creature-sheet-hp"><strong>Hit Points</strong> {creature.maxHp} {creature.hitPoints} {creature.hitDice ? "(" + creature.hitDice + ")" : "" }</p>
                        <p className="creature-sheet-speeds">
                            <strong>Speed</strong>{" "}
                            {Object.entries(creature.speeds)
                                .sort(([a], [b]) => (a === "walk" ? -1 : b === "walk" ? 1 : 0))
                                .map(([name, value], index, arr) => 
                                    value > 0 ? (
                                        <span key={name}>
                                            {name !== "walk" ? name + " " : ""} 
                                            {value} ft.
                                            {index < arr.length - 1 ? ", " : ""}
                                        </span>
                                ) : null
                            )}
                        </p>
                        <div className="creature-sheet-underscore-container">
                            <div className="creature-sheet-underscore"></div>
                        </div>
                    </div>
                    <div className="creature-sheet-stats">
                        {Object.entries(creature.abilities).map(([ability, value]) => (
                            <div><strong>{ability.substring(0, 3).toUpperCase()}</strong> <div>{value} ({(calcModifier(value)) >= 0 ? "+" + calcModifier(value) : calcModifier(value)})</div></div>
                        ))}
                    </div>
                    <div className="creature-sheet-underscore-container">
                        <div className="creature-sheet-underscore"></div>
                    </div>
                    <div className="creature-sheet-sub-stats">
                        {creature.savingThrows && Object.keys(creature.savingThrows).length > 0 ? (
                            <p>
                                <span><strong>Saving Throws </strong></span>
                                {Object.entries(creature.savingThrows).map(([name, value], index, arr) => (
                                    value ? (
                                        <span>
                                            {name.substring(0, 1).toUpperCase() + name.substring(1, 3)} 
                                            {value > 0 ? " +" + value : value}
                                            {index < arr.length - 1 ? ", " : ""}
                                        </span>
                                    ) : null
                                ))}
                            </p>
                        ) : null}
                        {creature.damageResistances ? (
                            <div className="creature-sheet-damage-resistances">
                                <p><strong>Damage Resistances</strong> {creature.damageResistances}</p>
                            </div>
                        ) : null}
                        {creature.damageVulnerabilities ? (
                            <div className="creature-sheet-damage-vulnerabilities">
                                <p><strong>Damage Vulnerabilities</strong> {creature.damageVulnerabilities}</p>
                            </div>
                        ) : null}
                        {creature.damageImmunities ? (
                            <div className="creature-sheet-damage-immunities">
                                <p><strong>Damage Immunities</strong> {creature.damageImmunities}</p>
                            </div>
                        ) : null}
                        {creature.conditionImmunities ? (
                            <div className="creature-sheet-condition-immunities">
                                <p><strong>Condition Immunities</strong> {creature.conditionImmunities}</p>
                            </div>
                        ) : null}
                        {creature.senses ? (
                            <div className="creature-sheet__senses">
                                <p><strong>Senses</strong> {creature.senses}</p>
                            </div>
                        ) : null}
                        <p><span><strong>Languages </strong>{creature.languages && creature.languages.length > 0 ? creature.languages : "—"}</span></p>
                        {creature.challengeRating ? (
                            <p><strong>Challenge </strong>{creature.challengeRating}</p>
                        ) : null}
                        
                    </div>

                    <div className="creature-sheet-underscore-container">
                        <div className="creature-sheet-underscore"></div>
                    </div>

                    {creature.specialAbilities && Object.entries(creature.specialAbilities).map(([name, description]) => (
                        <p><span><strong>{name}</strong></span> {description}</p>
                    ))}
                    
                    {Object.entries(creature.actions)
                    .sort(([a], [b]) => {
                        if (a === "action") return -1;     // "action" first
                        if (b === "action") return 1;
                        if (a === "legendary_action") return 1; // optionally control others
                        return a.localeCompare(b);         // alphabetical for remaining
                    })
                    .map(([type, actions]) => (
                        <>
                        {type === "action" && Object.keys(actions).length > 0 && (
                            <div className="creature-sheet__actions">
                            <h2>Actions</h2>
                            <hr />
                            {Object.entries(actions).map(([name, description]) => (
                                <p key={name}>
                                <strong>{name} </strong>
                                {description}
                                </p>
                            ))}
                            </div>
                        )}

                        {type === "legendary_action" && Object.keys(actions).length > 0 && (
                            <div className="creature-sheet__legendary-actions">
                            <h2>Legendary Actions</h2>
                            <hr />
                            {creature.legendaryDescription && <p>{creature.legendaryDescription}</p>}
                            {Object.entries(actions).map(([name, description]) => (
                                <p key={name}>
                                <strong>{name} </strong>
                                {description}
                                </p>
                            ))}
                            </div>
                        )}
                        </>
                    ))}
                </div>
            ) : (
                <div className="creature-sheet">
                    <p>Select a creature to view its details.</p>
                </div>
            )}
        
        </div>
    )
}