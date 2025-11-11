import { useState } from "react"
import { EditableField } from "./EditableFields";
import { PCClassData } from "../types/PCCLasses";
import { classInfo } from "./PCClassData";
import { PlayerCharacter } from "../types/PlayerCharacter";

const XP_TO_LEVEL: Record<number, number> = {
  1: 0,
  2: 300,
  3: 900,
  4: 2700,
  5: 6500,
  6: 14000,
  7: 23000,
  8: 34000,
  9: 48000,
  10: 64000,
  11: 85000,
  12: 100000,
  13: 120000,
  14: 140000,
  15: 165000,
  16: 195000,
  17: 225000,
  18: 265000,
  19: 305000,
  20: 355000
};

type PCHeaderProps = {
    playerCharacter: PlayerCharacter | null,
    setPlayerCharacter: React.Dispatch<React.SetStateAction<PlayerCharacter | null>>
};

export const PCHeader = ({playerCharacter, setPlayerCharacter} : PCHeaderProps) => {
    
    if (!playerCharacter) return null;

    const handleClassButtonClick = (className: string) => {
        setPlayerCharacter((prev) => prev ? ({...prev, clazz: className}) : null);
    }

    const handleNameChange = (newValue: string | number) => {
        const name = String(newValue);

        setPlayerCharacter(prev => prev ? ({...prev, name: name}) : null);
    }

    const handleExpirienceChange = (newValue: string | number) => {
        const experience = Number(newValue);
        if (isNaN(experience)) {
            return;
        }

        let level = getLevelFromXp(experience);

        setPlayerCharacter(prev => prev ? ({
            ...prev,  
            experience: experience,
            level: level,
            proficiencyBonus: 1+(Math.ceil(level / 4))
        }) : null);
    }

    return (
        <div className="pc-form__header">
            <div className="pc-form__name"><EditableField value={playerCharacter.name} onChange={(newValue) => handleNameChange(newValue)}/></div>
            <div className="pc-form__class">
                <div className="pc-form__class-backdrop">
                    {Object.keys(classInfo).map((pcClassName) => (
                        <button
                            key={pcClassName}
                            className="pc-form__class-option"
                            onClick={() => handleClassButtonClick(pcClassName)}
                        >
                            {pcClassName}
                        </button>
                    ))}
                </div>
            </div>
            <div className="pc-form__expirience">
                <p>Level: {playerCharacter.level}</p>
                <p>Proficiency Bonus: {playerCharacter.proficiencyBonus}</p>
                <span>Experience: </span>
                <EditableField 
                    value={playerCharacter.experience}
                    onChange={(newValue) => handleExpirienceChange(newValue)}
                />
            </div>
            <div className="pc-form__proficiency-bonus"></div>
            <button className="pc-form__lvl-up-button"></button>
        </div>
    )
}

const getLevelFromXp = (xp: number) => {
    const levels = Object.entries(XP_TO_LEVEL)
    .map(([level, threshold]) => [parseInt(level), threshold] as [number, number])
    .sort((a, b) => a[1] - b[1]);

    let currentLevel = 1;

    for (const [level, threshold] of levels) {
        if (xp >= threshold) {
            currentLevel = level;
        } else {
            break;
        }
    }

    return currentLevel;
}