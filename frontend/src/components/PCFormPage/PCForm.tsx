import { useEffect, useState } from "react";
import { PCAbilities } from "./components/PCAbilities";
import { PCArmorClassSpeed, Speed } from "./components/PCArmorClassSpeed";
import { PCDetails } from "./components/PCDetails";
import { PCHeader } from "./components/PCHeader";
import { PCHitPoints } from "./components/PCHitPoints";
import { PCSkills } from "./components/PCSkills";
import "./PCForm.css";
import { calcModifier } from "../dndUtils";
import { Ability, PCClassData, Skill, SKILL_TO_ABILITY } from "./types/PCCLasses";
import { PlayerCharacter, SkillData } from "./types/PlayerCharacter";
import { classInfo } from "./components/PCClassData";
import { useOnFormMount } from "./hooks/useOnFormMount";
import { Player } from "../Lobby/type/Player";
import { EditableField } from "./components/EditableFields";
import { useHandleSaveButtonClick } from "./hooks/useHandleSaveButtonClick";
import { useParams } from "react-router-dom";

export const PCForm = () => {

    const {characterId} = useParams<{characterId : string}>();
    const [playerCharacter, setPlayerCharacter] = useState<PlayerCharacter | null>(null);
    const {onFormMount, isFetchingPlayerCharacter} = useOnFormMount({ setPlayerCharacter });
    const handleSaveButtonClick = useHandleSaveButtonClick();

    const [tempSenses, setTempSenses] = useState(() => ({
        passive: {
            insight: playerCharacter?.senses.passive.insight,
            investigation: playerCharacter?.senses.passive.investigation,
            perception: playerCharacter?.senses.passive.perception
        },
        active: {
            darkvision: playerCharacter?.senses.active.darkvision,
            blindsight: playerCharacter?.senses.active.blindsight,
            tremorsense: playerCharacter?.senses.active.tremorsense,
            truesight: playerCharacter?.senses.active.truesight
        }
    }));

    useEffect(() => {
        if (playerCharacter) setTempSenses(playerCharacter?.senses);
    }, [playerCharacter])

    useEffect(() => {
        const num = Number(characterId);
        if (num) {
            onFormMount(num);
        }
    }, []);

    if (isFetchingPlayerCharacter()) {
        return (
            <div>Wainting for your character to load...</div>
        )
    }

    const handleTempSensesChange = (name: string, value: string) => {
        
        setTempSenses((prev) => {
            const [section, category, senseName] = name.split(".");
            if (category !== "active" && category !== "passive") throw new Error("category of senses is not active or passive");

            const updated = structuredClone(prev);

            console.log("Updated:",updated);
            if (section === "senses" && category && senseName) {
                (updated[category] as Record<string, string | number>)[senseName] = value;
            }

            return updated;
        });
    }
    
    const handleSensesCommit = (name: string, value: string) => {
        const num = Number(value);
        if (isNaN(num)) return;

        setPlayerCharacter((prev: any) => {
            if (!prev) return null;
            const [section, category, senseName] = name.split(".");
            const updated = structuredClone(prev);
            if (section === "senses" && category && senseName) {
                updated.senses[category][senseName] = num;
            }
                return updated;
        });
    };


    const handleLanguageChange = (value: string | undefined) => {
        setPlayerCharacter(prev => {
            if (!prev) return null;
            return {
                ...prev,
                languages: value
            }
        })
    }

    return (
        <div className="pc-form__container">
            <PCHeader
                playerCharacter={playerCharacter}
                setPlayerCharacter={setPlayerCharacter}
            />
            <PCHitPoints
                playerCharacter={playerCharacter}
                setPlayerCharacter={setPlayerCharacter}
            />
            <PCAbilities 
                playerCharacter={playerCharacter}
                setPlayerCharacter={setPlayerCharacter}
            />
            <PCArmorClassSpeed
                playerCharacter={playerCharacter}
                setPlayerCharacter={setPlayerCharacter}
            />
            <div className="pc-form__test-container">
                <PCSkills 
                    playerCharacter={playerCharacter}
                    setPlayerCharacter={setPlayerCharacter}
                />
                <PCDetails 
                    playerCharacter={playerCharacter}
                    setPlayerCharacter={setPlayerCharacter}
                />
                <div className="pc-form__additional-info">
                    <div className="pc-form__defenses">
                        <h3>Defenses</h3>

                        <div className="pc-form__defense-item">
                            <label>Resistances</label>
                            <textarea 
                                name="defenses.resistances"
                                placeholder="e.g. fire, poison"
                            ></textarea>
                        </div>
                        <div className="pc-form__defense-item">
                            <label>Vulnerabilities</label>
                            <textarea 
                                name="defenses.vulnerabilities"
                                placeholder="e.g. radiant, psychic"
                            ></textarea>
                        </div>
                        <div className="pc-form__defense-item">
                            <label>Immunities</label>
                            <textarea 
                                name="defenses.immunities"
                                placeholder="e.g. necrotic, exhaustion"
                            ></textarea>
                        </div>
                    </div>

                    <div className="pc-form__conditions">
                        <h3>Conditions</h3>
                        <textarea 
                            name="conditions"
                            placeholder="e.g. frightened, charmed, poisoned"
                        ></textarea>
                    </div>

                    <div className="pc-form__senses">
                <h3>Senses</h3>

                <fieldset>
                    <legend>Passive Senses</legend>

                    <div>
                    <label>Passive Investigation</label>
                    <input
                        name="senses.passive.investigation"
                        value={tempSenses.passive.investigation}
                        onChange={(e) => handleTempSensesChange(e.target.name, e.target.value)}
                        onBlur={(e) => handleSensesCommit(e.target.name, e.target.value)}
                        inputMode="numeric"
                    />
                    </div>

                    <div>
                    <label>Passive Insight</label>
                    <input
                        name="senses.passive.insight"
                        value={tempSenses.passive.insight}
                        onChange={(e) => handleTempSensesChange(e.target.name, e.target.value)}
                        onBlur={(e) => handleSensesCommit(e.target.name, e.target.value)}
                        inputMode="numeric"
                    />
                    </div>

                    <div>
                    <label>Passive Perception</label>
                    <input
                        name="senses.passive.perception"
                        value={tempSenses.passive.perception}
                        onChange={(e) => handleTempSensesChange(e.target.name, e.target.value)}
                        onBlur={(e) => handleSensesCommit(e.target.name, e.target.value)}
                        inputMode="numeric"
                    />
                    </div>
                </fieldset>

                <fieldset>
                    <legend>Active Senses</legend>

                    <div>
                    <label>Darkvision</label>
                    <input
                        name="senses.active.darkvision"
                        placeholder="in feet"
                        value={tempSenses.active.darkvision}
                        onChange={(e) => handleTempSensesChange(e.target.name, e.target.value)}
                        onBlur={(e) => handleSensesCommit(e.target.name, e.target.value)}
                        inputMode="numeric"
                    />
                    </div>

                    <div>
                    <label>Blindsight</label>
                    <input
                        name="senses.active.blindsight"
                        placeholder="in feet"
                        value={tempSenses.active.blindsight}
                        onChange={(e) => handleTempSensesChange(e.target.name, e.target.value)}
                        onBlur={(e) => handleSensesCommit(e.target.name, e.target.value)}
                        inputMode="numeric"
                    />
                    </div>

                    <div>
                    <label>Truesight</label>
                    <input
                        name="senses.active.truesight"
                        placeholder="in feet"
                        value={tempSenses.active.truesight}
                        onChange={(e) => handleTempSensesChange(e.target.name, e.target.value)}
                        onBlur={(e) => handleSensesCommit(e.target.name, e.target.value)}
                        inputMode="numeric"
                    />
                    </div>

                    <div>
                    <label>Tremorsense</label>
                    <input
                        name="senses.active.tremorsense"
                        placeholder="in feet"
                        value={tempSenses.active.tremorsense}
                        onChange={(e) => handleTempSensesChange(e.target.name, e.target.value)}
                        onBlur={(e) => handleSensesCommit(e.target.name, e.target.value)}
                        inputMode="numeric"
                    />
                    </div>
                </fieldset>
                </div>


                    <div className="pc-form__proficiencies-languages">
                        <h3>Proficiencies</h3>
                        <div className="proficiencies-list">
                            <div className="proficiency-entry">
                            <select name="proficiencies[].category">
                                <option value="tool">Tool</option>
                                <option value="weapon">Weapon</option>
                                <option value="armor">Armor</option>
                            </select>

                            <input 
                                type="text" 
                                name="proficiencies[].name" 
                                placeholder="e.g. Longsword, Thieves’ Tools"
                            />

                            <select name="proficiencies[].level">
                                <option value="half_proficient">Half Proficient</option>
                                <option value="proficient">Proficient</option>
                                <option value="expertise">Expertise</option>
                            </select>
                            </div>
                        </div>

                        <h3>Languages</h3>
                        <textarea 
                            name="languages"
                            value={playerCharacter?.languages}
                            onChange={e => handleLanguageChange(e.target.value)}
                            placeholder="e.g. Common, Elvish, Draconic"
                        ></textarea>
                    </div>

                </div>
            </div>
            <div className="pc-form__controlls">
                <button className="pc-form__controll-item" id="pc-form__controlls-cancel-button">Cancel</button>
                <button 
                    className="pc-form__controll-item" 
                    id="pc-form__controlls-save-button"
                    onClick={() => handleSaveButtonClick({playerCharacter})}
                >Save</button>
            </div>
        </div>
            
    )
}

