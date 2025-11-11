import { PlayerCharacter } from "../types/PlayerCharacter"
import { EditableField } from "./EditableFields"
export type Speed = {
    walk: number,
    swim: number,
    fly: number,
    climb: number
}
type PCArmorClassSpeedProps = {
    playerCharacter: PlayerCharacter | null,
    setPlayerCharacter: React.Dispatch<React.SetStateAction<PlayerCharacter | null>>
}

export const PCArmorClassSpeed = ({ playerCharacter, setPlayerCharacter }: PCArmorClassSpeedProps) => {
    
    if (!playerCharacter) return null;

    const handleArmorClassChange = (value: string | number) => {
        const num = Number(value);
        if (isNaN(num) || num < 0 || num > 30) return;
        setPlayerCharacter(prev => prev ? ({
            ...prev,
            armorClass: num
        }) : null);
    }

    const handleSpeedChange = (field: keyof Speed, value: string | number) => {
        const num = Number(value);
        if (isNaN(num) || num < 0) return;
        setPlayerCharacter(prev => prev ? ({
            ...prev,
            speed: {
                ...prev.speed,
                [field]: num
            }
        }) : null);
    }

    return(
        <div className="pc-form__ac-speed">
            <div className="pc-form__armor-class">
                <div className="pc-form__armor-class-value">
                    <span>Armor Class: </span>
                    <EditableField 
                        value={playerCharacter.armorClass}
                        onChange={(newValue) => handleArmorClassChange(newValue)}
                    />
                </div>
                <div className="pc-form__armor-backdrop">
                    <div className="pc-form__armor-type"></div>
                    <div className="pc-form__armor-value"></div>
                    <div className="pc-form__dexterity-modifier"></div>
                    <div className="pc-form__shield-value"></div>
                </div>
            </div>
            <div className="pc-form__speed">
                <div className="pc-form__speed-walk-value">
                    <span>Walk Speed: </span>
                    <EditableField
                        value={playerCharacter.speed.walk}
                        onChange={(newValue) => handleSpeedChange("walk", newValue)}
                    />
                </div>
                <div className="pc-form__speed-backdrop">
                    <div className="pc-form__speed-swim"></div>
                    <div className="pc-form__speed-fly"></div>
                    <div className="pc-form__speed-climb"></div>
                </div>
            </div>
        </div>
    )
}