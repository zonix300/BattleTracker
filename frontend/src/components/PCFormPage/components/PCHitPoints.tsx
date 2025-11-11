import { useState } from "react"
import { HPData } from "../types/HPData";
import { PlayerCharacter } from "../types/PlayerCharacter";

type PCHitPointsProps = {
    playerCharacter: PlayerCharacter | null,
    setPlayerCharacter: React.Dispatch<React.SetStateAction<PlayerCharacter | null>>
}

export const PCHitPoints = ({ playerCharacter, setPlayerCharacter }: PCHitPointsProps) => {

    const [deltaHp, setDeltaHp] = useState(0);

    if (!playerCharacter) return null;


    const handleHpChange = (field: keyof HPData, value: string | number) => {
        let num = Number(value);
        if (isNaN(num)) return;

        setPlayerCharacter(prev => {
            if (!prev) return null;
            const newData = { ...prev.hp };

            if (field === "current") {
                newData.current = Math.max(0, Math.min(num, prev.hp.max));

            } else if (field === "max") {
                num = Math.max(0, num);
                newData.max = num;
                newData.current = Math.min(prev.hp.current, num);

            } else if (field === "temporary") {
                newData.temporary = Math.max(0, num);

            }

            return {...prev, hp: newData};
        });
    };

    const handleDeltaHpChange = (value: string) => {
        const num = Number(value);
        if (isNaN(num)) return;

        setDeltaHp(num);
    }

    const handleDamage = (value: number) => {
        handleHpChange("current", playerCharacter.hp.current-value);
    }

    const handleHeal = (value: number) => {
        handleHpChange("current", playerCharacter.hp.current+value);
    }


    return (
        <div className="pc-form__hit-points">
            <input 
                type="text" 
                className="pc-form__hp-current"
                value={playerCharacter.hp.current}
                onChange={(e) => handleHpChange("current", e.target.value)}
            />/
            <input 
                type="text" 
                className="pc-form__hp-max"
                value={playerCharacter.hp.max}
                onChange={(e) => handleHpChange("max", e.target.value)}
            />
            <input 
                type="text" 
                className="pc-form__hp-temporary"
                value={playerCharacter.hp.temporary}
                onChange={(e) => handleHpChange("temporary", e.target.value)}
            />
            <div className="pc-form__hp-controls">
                <button 
                    className="pc-form__hp-damage"
                    onClick={() => handleDamage(deltaHp)}
                >-</button>
                <input 
                    className="pc-form__hp-controls-input" 
                    type="text"
                    value={deltaHp}
                    onChange={(e) => handleDeltaHpChange(e.target.value)}
                />
                <button 
                    className="pc-form__hp-heal"
                    onClick={() => handleHeal(deltaHp)}
                >+</button>
            </div>
        </div>
    )
}