import {useState, useEffect} from "react";
import { Creature } from "../type/Creature";
import { apiClient } from "../../services/api";
import { useSearchCreature } from "../../hooks/useSeachCreature";
import "./CreatureSheet.css"

const API_BASE_URL = process.env.REACT_APP_API_BASE_URL

interface CreatureSheetProps {
    templateCreatureId: number | null;
}

export default function CreatureSheet({ templateCreatureId }: CreatureSheetProps) {
    
    const [creature, setCreature] = useState<Creature | null>(null);

    const {
        handleSelectedCreatureChange,
        isSelectedCreatureChanging
    } = useSearchCreature(templateCreatureId, setCreature);


    useEffect(() => {
        if (templateCreatureId) {
            handleSelectedCreatureChange();
        }
        
    }, [templateCreatureId]);

    return (
        <div className="creature-sheet-container">
            {creature ? (
                <div className="creature-sheet">
                    <div className="creature-sheet-main">
                        <p className="creature-sheet-header"><span>{creature.name}</span></p> 
                        <p className="creature-sheet-subheader">{creature.creatureType} {creature.size}, {creature.alignment}</p>
                        <div className="creature-sheet-underscore-container">
                            <div className="creature-sheet-underscore"></div>
                        </div>
                        <p className="creature-sheet-ac"><strong>Armor Class</strong> {creature.armorClass} {creature.armorDescription ? (creature.armorDescription) : ""}</p>
                        <p className="creature-sheet-hp"><strong>Hit Points</strong> {creature.hitPoints} {creature.hitDice ? "(" + creature.hitDice + ")" : "" }</p>
                        <p className="creature-sheet-speeds"><strong>Speed</strong> {creature.speeds.map((speed) => (
                            <span key={speed.id.speedType}>
                                {speed.name} {speed.value} ft. </span>
                        ))}
                        </p>
                        <div className="creature-sheet-underscore-container">
                            <div className="creature-sheet-underscore"></div>
                        </div>
                    </div>
                    <div className="creature-sheet-stats">
                        <div><strong>STR</strong> <div>{creature.strength}</div></div>
                        <div><strong>DEX</strong> <div>{creature.dexterity}</div></div>
                        <div><strong>CON</strong> <div>{creature.constitution}</div></div>
                        <div><strong>INT</strong> <div>{creature.intelligence}</div></div>
                        <div><strong>WIS</strong> <div>{creature.wisdom}</div></div>
                        <div><strong>CHA</strong> <div>{creature.charisma}</div></div>
                    </div>
                    <div className="creature-sheet-underscore-container">
                        <div className="creature-sheet-underscore"></div>
                    </div>
                    <div className="creature-sheet-sub-stats">
                        {creature.strengthSave || creature.dexteritySave || creature.constitutionSave || creature.intelligenceSave || creature.wisdomSave || creature.charismaSave ? (
                            <p>
                                <span><strong>Saving Throws </strong></span>
                                {
                                    [
                                        creature.strengthSave ? `Str +${creature.strengthSave}` : null,
                                        creature.dexteritySave ? `Dex +${creature.dexteritySave}` : null,
                                        creature.constitutionSave ? `Con +${creature.constitutionSave}` : null,
                                        creature.intelligenceSave ? `Int +${creature.intelligenceSave}` : null,
                                        creature.wisdomSave ? `Wis +${creature.wisdomSave}` : null,
                                        creature.charismaSave ? `Cha +${creature.charismaSave}` : null,
                                    ]
                                    .filter(Boolean) // remove nulls
                                    .join(", ")
                                }
                            </p>
                        ) : null}
                        {creature.senses ? (
                            <p>
                                <span><strong>Senses </strong>{creature.senses}</span>
                            </p>
                        ) : null}
                        <p><span><strong>Languages </strong>{creature.languages}</span></p>
                            
                        <p><strong>Challenge </strong>{creature.challengeRating}</p>
                    </div>
                    <div className="creature-sheet-underscore-container">
                        <div className="creature-sheet-underscore"></div>
                    </div>

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

                    {creature.abilities && creature.abilities.length > 0 ? (
                        <div>
                            {creature.abilities.map((ability) => (
                                <p key={ability.id}>
                                    <strong>{ability.name} </strong> {ability.description}
                                </p>
                            ))}
                        </div>
                    ) : null}
                    
                    {creature.actions && creature.actions.length > 0 ? (
                        <div>
                            <p className="creature-sheet-paragraph-title">Actions</p>
                            <hr/>
                            <div>
                                {creature.actions.map((action) => (
                                    <p key={action.id}>
                                        <strong>{action.name} </strong> {action.description}
                                    </p>
                                ))}
                            </div>
                        </div>
                    ) : null}

                    {creature.bonusActions && creature.bonusActions.length > 0 ? (
                        <div>
                            <p className="creature-sheet-paragraph-title">Bonus Actions</p>
                            <hr/>
                            <div>
                                {creature.bonusActions.map((action) => (
                                    <span key={action.id}>
                                        <strong>{action.name}</strong> {action.description}
                                    </span>
                                ))}
                            </div>
                        </div>
                    ) : null}

                    {creature.reactions && creature.reactions.length > 0 ? (
                        <div>
                            <p className="creature-sheet-paragraph-title">Reactions</p>
                            <hr/>
                            <div>
                                {creature.reactions.map((action) => (
                                    <span key={action.id}>
                                        <strong>{action.name}</strong> {action.description}
                                    </span>
                                ))}
                            </div>
                        </div>
                    ) : null}

                    {creature.legendaryActions && creature.legendaryActions.length > 0 ? (
                        <div>
                            <p className="creature-sheet-paragraph-title">Legendary Actions</p>
                            <hr/>
                            <p>{creature.legendaryDescription}</p>
                            <div>
                                {creature.legendaryActions.map((action) => (
                                    <p key={action.id}>
                                        <strong>{action.name}</strong> {action.description}
                                    </p>
                                ))}
                            </div>
                        </div>
                    ) : null}


                    

                </div>
            ) : (
                <div className="creature-sheet">
                    <p>Select a creature to view its details.</p>
                </div>
            )}
        
        </div>
    )
}