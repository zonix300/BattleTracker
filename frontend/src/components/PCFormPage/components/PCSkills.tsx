import { useEffect, useState } from "react"
import { EditableField } from "./EditableFields"
import { PCClassData, Skill } from "../types/PCCLasses"
import { classInfo } from "./PCClassData"
import { PlayerCharacter, SkillData } from "../types/PlayerCharacter"

type PCSkillsProps = {
    playerCharacter: PlayerCharacter | null,
    setPlayerCharacter: React.Dispatch<React.SetStateAction<PlayerCharacter | null>>
}

export const PCSkills = ({ playerCharacter, setPlayerCharacter } : PCSkillsProps) => {

    const [chosenSkills, setChosenSkills] = useState<Skill[]>([]);
    const [classSkillData, setClassSkillData] = useState<{
        maxChoice: number,
        availableSkills: Skill[]
    }>({
        maxChoice: 0,
        availableSkills: []
    });

    useEffect(() => {
        if (playerCharacter?.clazz) {
            const [maxChoice, availableSkills] = classInfo[playerCharacter.clazz].skillProficiencies;
            setClassSkillData({ maxChoice, availableSkills });
            setChosenSkills([]);
            setPlayerCharacter(prev => {
                if (!prev) return null;

                const newSkills = Object.fromEntries(
                    Object.entries(prev.skills).map(([skill, data]) => {
                        const total = data.baseValue + data.otherBonuses;
                        return [
                            skill,
                            { 
                                ...data, 
                                proficiency: false,
                                total: total
                            }
                    ]}) 
                ) as Record<Skill, SkillData>

                return {
                ...prev,
                skills: newSkills
            }})
        } else {
            setClassSkillData({ maxChoice: 0, availableSkills: []});
        }
    }, [playerCharacter?.clazz]);

    if (!playerCharacter) return null;


    const handleSkillValueChange = (skill: keyof typeof playerCharacter.skills, newValue: string | number) => {
        const num = Number(newValue);
        if (isNaN(num)) return;

        setPlayerCharacter(prev => prev ? ({
            ...prev,
            skills: {
                ...prev.skills,
                [skill]: {
                    ...prev.skills[skill],
                    total: num
                }
            }
        }) : null)
    }

    const handleToggleSkillProficiency = (skill: Skill) => {
        setPlayerCharacter(prev => {
            if (!prev) return null;
            const proficiency = !prev.skills[skill].proficiency;
            let total;
            if (proficiency) {
                total = prev.skills[skill].baseValue + playerCharacter.proficiencyBonus + prev.skills[skill].otherBonuses;
            } else {
                total = prev.skills[skill].baseValue + prev.skills[skill].otherBonuses;
            }
            return {
                ...prev,
                skills: {
                    ...prev.skills,
                    [skill]: {
                        ...prev.skills[skill],
                        proficiency: proficiency,
                        total: total
                    }
                }
            }
        }) 
        setChosenSkills((prev) => {
            if (prev.includes(skill)) return prev.filter((s) => s !== skill);
            if (prev.length >= classSkillData.maxChoice || !classSkillData.availableSkills.includes(skill)) return prev;
            return [...prev, skill];
        });
    }
    
    return (
        <div className="pc-form__skills">
            {Object.entries(playerCharacter.skills).map(([skill, data]) => (
                <div 
                    key={skill}
                    id={`pc-form__${skill}`}
                    className="pc-form__skill"
                >
                    <div className="pc-form__skill-main">
                        <div className="pc-form__skill-name">{skill.split("_").map((word) => word.charAt(0).toUpperCase() + word.slice(1)).join(" ")}</div>
                        <div className="pc-form__skill-ability-name">{data.ability.substring(0, 3)}</div>
                    </div>
                    <div className="pc-form__skill-sub">
                        <div className="pc-form__skill-value">
                            <EditableField
                                value={data.total}
                                onChange={(newValue) => handleSkillValueChange(skill as Skill, newValue)}
                            />
                        </div>
                        <div className="pc-form__skill-proficiency-backdrop">
                            <input 
                                type="checkbox"
                                checked={playerCharacter.skills[skill as Skill].proficiency}
                                onChange={() => handleToggleSkillProficiency(skill as Skill)}
                                disabled={
                                    !chosenSkills.includes(skill as Skill) && 
                                    (!classSkillData.availableSkills.includes(skill as Skill) ||
                                    chosenSkills.length >= classSkillData.maxChoice)
                                }
                            />
                        </div>
                    </div>
                    
                </div>
            ))}
        </div>
    )
}