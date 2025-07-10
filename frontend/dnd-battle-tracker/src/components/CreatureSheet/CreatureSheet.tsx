import {useState, useEffect} from "react";
import { Creature } from "../type/Creature";
import { apiClient } from "../../services/api";

const API_BASE_URL = process.env.REACT_APP_API_BASE_URL

interface CreatureSheetProps {
    templateCreatureId: number | null;
}

export default function CreatureSheet({ templateCreatureId }: CreatureSheetProps) {
    
    const [creature, setCreature] = useState<Creature | null>(null);


    useEffect(() => {
        const fetchCreature = async () => {
            if (!templateCreatureId) {
                setCreature(null);
                return;
            }
            
            try {
                const response = await apiClient.get<Creature>(`${API_BASE_URL}/creatures/${templateCreatureId}`);
                setCreature(response.data);
            } catch (error) {
                console.error("Error fetching creature:", error);
                setCreature(null);
            }
        };

            fetchCreature();    
        }, [templateCreatureId]);
  
    

    return (
        <div className="creature-sheet-container">
            {creature ? (
                <div className="creature-sheet">
                    <h2>{creature.name}</h2>
                    <p><strong>Type:</strong> {creature.creatureType}</p>
                    <p><strong>Size:</strong> {creature.size}</p>
                    <p><strong>Alignment:</strong> {creature.alignment}</p>
                    <p><strong>Armor Class:</strong> {creature.armorClass} ({creature.armorDescription})</p>
                    <p><strong>Hit Points:</strong> {creature.hitPoints} ({creature.hitDice})</p>
                    <p><strong>Speed:</strong>{creature.speeds.map((speed) => (
                        <span key={speed.id.speedType}>
                        {speed.name} {speed.value} ft.
                        </span>
                    ))}
                    </p>
                    <p><strong>Challenge Rating:</strong> {creature.challengeRating}</p>
                    <p><strong>Strength:</strong> {creature.strength}</p>
                    <p><strong>Dexterity:</strong> {creature.dexterity}</p>
                    <p><strong>Constitution:</strong> {creature.constitution}</p>
                    <p><strong>Intelligence:</strong> {creature.intelligence}</p>
                    <p><strong>Wisdom:</strong> {creature.wisdom}</p>
                    <p><strong>Charisma:</strong> {creature.charisma}</p>

                    
                    <p><strong>Damage Resistances:</strong> {creature.damageResistances}</p>
                    <p><strong>Damage Vulnerabilities:</strong> {creature.damageVulnerabilities}</p>
                    <p><strong>Damage Immunities:</strong> {creature.damageImmunities}</p>
                    <p><strong>Condition Immunities:</strong> {creature.conditionImmunities}</p>

                    {creature.abilities && creature.abilities.length > 0 ? (
                        <p>
                            {creature.abilities.map((ability) => (
                                <span key={ability.id}>
                                    <strong>{ability.name} </strong> {ability.description}
                                </span>
                            ))}
                        </p>
                    ) : null}
                    
                    {creature.actions && creature.actions.length > 0 ? (
                        <p>
                            {creature.actions.map((action) => (
                                <span key={action.id}>
                                    <strong>{action.name} </strong> {action.description}
                                </span>
                            ))}
                        </p>
                    ) : null}

                    {creature.bonusActions && creature.bonusActions.length > 0 ? (
                        <p>
                            {creature.bonusActions.map((action) => (
                                <span key={action.id}>
                                    <strong>{action.name}</strong> {action.description}
                                </span>
                            ))}
                        </p>
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