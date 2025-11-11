import { useState } from "react"
import { Attack, PlayerCharacter } from "../../types/PlayerCharacter"

type Props = {
    setPlayerCharacter: React.Dispatch<React.SetStateAction<PlayerCharacter | null>>
    onClose: () => void;
}

export const AttackModal = ({ setPlayerCharacter, onClose } : Props) => {
    const [newAttack, setNewAttack] = useState<Attack>({
        name: "unnamed",
        type: "melee",
        linkedWeapon: "",
        range: 5,
        hit_dc: 0,
        actionType: "action",
        damage: {
            type: "force",
            ability: "strength",
            otherBonuses: 0,
            diceNumber: 0,
            diceType: 0
        }
    });

    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const {name, value} = e.target;

        setNewAttack(prev => ({
            ...prev,
            [name]: value
        }));
    }

    const handleDamageChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const { name, value } = e.target;
        setNewAttack(prev => ({
            ...prev,
            damage: {
                ...prev.damage,
                [name]: value
            }
        }));
    };

    const handleSave = () => {
        setPlayerCharacter(prev => {
            if (!prev) return null;

            return {
                ...prev,
                combat: {
                    ...prev.combat,
                    attacks: [...(prev.combat.attacks.length > 0 ? prev.combat.attacks : []), newAttack]
                }
            }

        });
    };


    
    return (
        <div className="attack-modal__container">
            <input name="name" value={newAttack.name} onChange={handleChange} placeholder="Name" />
            <input name="type" value={newAttack.type} onChange={handleChange} placeholder="Type" />
            <input name="linkedWeapon" value={newAttack.linkedWeapon} onChange={handleChange} placeholder="Weapon" />
            <input name="range" value={newAttack.range} onChange={handleChange} placeholder="Range" />
            <input name="hit_dc" value={newAttack.hit_dc} onChange={handleChange} placeholder="Hit" />
            <div className="attack-modal__damage">
                <input
                    name="type"
                    value={newAttack.damage.type}
                    onChange={handleDamageChange}
                    placeholder="Damage Type"
                />
                <input
                    name="ability"
                    value={newAttack.damage.ability}
                    onChange={handleDamageChange}
                    placeholder="Ability"
                />
                <input
                    name="diceNumber"
                    value={newAttack.damage.diceNumber}
                    onChange={handleDamageChange}
                    placeholder="Dice Number"
                />
                <input
                    name="diceType"
                    value={newAttack.damage.diceType}
                    onChange={handleDamageChange}
                    placeholder="Dice Type"
                />
            </div>
            <div className="attack-modal__buttons">
                <button onClick={handleSave}>Save</button>
                <button onClick={onClose}>Cancel</button>
            </div>
            </div>

    )
}