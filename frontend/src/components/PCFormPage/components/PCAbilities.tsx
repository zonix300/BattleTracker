import { useEffect, useState } from "react";
import { EditableField } from "./EditableFields";
import { calcModifier } from "../../dndUtils";
import { PlayerCharacter, SkillData } from "../types/PlayerCharacter";
import { Ability, Skill, SKILL_TO_ABILITY } from "../types/PCCLasses";

type PCAbilitiesProps = {
    playerCharacter: PlayerCharacter | null,
    setPlayerCharacter: React.Dispatch<React.SetStateAction<PlayerCharacter | null>>
}

export const PCAbilities = ({playerCharacter, setPlayerCharacter} : PCAbilitiesProps) => {

    useEffect(() => {
            setPlayerCharacter((prev) => {
                if (!prev) return null;
                
                const updatedSkills: Record<Skill, SkillData> = Object.fromEntries(
                    Object.entries(prev.skills).map(([skill, data]) => {
                        const baseValue = calcSkillValue(skill);
                        console.log("Skill's", skill, "ability:", data.ability, "base value:", baseValue);
                        const total = baseValue + data.otherBonuses;
    
                        return [
                            skill,
                            {
                                ...data,
                                baseValue,
                                total
                            }
                        ];
                    })
                ) as Record<Skill, SkillData>;
    
                return {
                    ...prev,
                    skills: updatedSkills
                }
            });
        }, [playerCharacter?.abilities]);

    if (!playerCharacter) return null;

    const calcSkillValue = (skill: keyof typeof SKILL_TO_ABILITY) => {
        const ability = SKILL_TO_ABILITY[skill];
        return calcModifier(playerCharacter.abilities[ability as Ability]);
    }
    
    const handleAbilityChange = (ability: Ability, newValue: string | number) => {
        const num = Number(newValue);
        if (isNaN(num)) return;

        setPlayerCharacter(prev => prev ? ({
            ...prev,
            abilities: {
                ...prev.abilities,
                [ability]: num
            }
        }) : null);
    };

    return (
        <div className="pc-form__abilities">
            {Object.entries(playerCharacter.abilities).map(([ability, value]) => (
                <div
                    key={ability} 
                    className="pc-form__ability-container"
                >
                    <div id={`pc-form__${ability}`} className="pc-form__ability">
                        <div className="pc-form__ability-value">
                            <EditableField
                                value={value}
                                onChange={(newValue) => handleAbilityChange(ability as Ability, newValue)}
                            />
                        </div>
                        <div className="pc-form__ability-modifier">
                            <span className="pc-form__ability-modifier-value">{calcModifier(playerCharacter.abilities[ability as Ability])}</span>
                        </div>
                    </div>
                    <div id={`pc-form__${ability}-save`} className="pc-form__ability-save">
                        <div className="pc-form__ability-save-modifier"></div>
                        <div className="pc-form__ability-save-proviciency"></div>
                    </div>
                </div>
            ))}
        </div>
    )
}