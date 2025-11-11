import { useState } from "react";
import { PlayerCharacter } from "../types/PlayerCharacter";
import { DetailElement } from "../types/DetailElement";
import { useModalMager } from "./Modals/hooks/useModalManager";

const Details = [
    "combat",
    "spells",
    "inventory",
    "feature_traits",
    "notes",
    "about"
]

const detailElements: DetailElement[] = [
    DetailElement.Attack,
    DetailElement.Mastery,
    DetailElement.Effect,
    DetailElement.Action
]

type PCDetailsProps = {
    playerCharacter: PlayerCharacter | null,
    setPlayerCharacter: React.Dispatch<React.SetStateAction<PlayerCharacter | null>>
}
export const PCDetails = ({ playerCharacter, setPlayerCharacter }: PCDetailsProps) => {


    
    const [selectedDetail, setSelectedDetail] = useState<string>("combat");
    const [backdropOpen, setBackdropOpen] = useState(false);
    const [activeModal, setActiveModal] = useState<DetailElement | null>(null);

    const onClose = () => {
        setActiveModal(null);
    }

    const manageModal = useModalMager({setPlayerCharacter, onClose});
    
    if (!playerCharacter) return null;

    const handleAddNewButtonClick = () => {
        setBackdropOpen(!backdropOpen);
    }

    const handleBackdropItemClick = (item: DetailElement) => {
        setActiveModal(item);
    }



    return (
        <div className="pc-form__details-container">
            <div className="pc-form__details-choose-bar">
                {Details.map(detail => (
                    <button 
                        key={detail}
                        onClick={() => setSelectedDetail(detail)}
                    >{detail}</button>
                ))}
            </div>
            {selectedDetail === "combat" && (
                <div className="pc-form__combat">
                    <div className="pc-form__combat-search-controls">
                        <input className="pc-form__combat-search-input" type="text" />
                        <button 
                            className="pc-form__add-new-button"
                            onClick={handleAddNewButtonClick}    
                        >Add New</button>
                        {backdropOpen ? (
                            <div className="pc-form__add-new-backdrop">
                                {detailElements.map((element) => (
                                    <button
                                        onClick={() => handleBackdropItemClick(element)}
                                    >
                                        {element}
                                    </button>
                                ))}
                            </div>
                        ) : null}
                        {activeModal ? (
                            manageModal(activeModal)
                        ) : null}
                    </div>
                    <div className="pc-form__combat-item" id="pc-form__attacks">
                        <table className="pc-form__combat-table">
                            <thead className="pc-form__combat-table-header">
                                <th>Attack Name</th>
                                <th>Range</th>
                                <th>Hit/DC</th>
                                <th>Damage</th>
                                <th>Controlls</th>
                            </thead>
                            <tbody>
                                {playerCharacter.combat && 
                                playerCharacter.combat.attacks && 
                                playerCharacter.combat.attacks.length > 0 && 
                                playerCharacter.combat.attacks.map(attack => (
                                    <tr className="pc-form__combat-table-row">
                                        <td className="pc-form__combat-table-item">{attack.name}</td>
                                        <td className="pc-form__combat-table-item">{attack.range} ft.</td>
                                        <td className="pc-form__combat-table-item">{attack.hit_dc >= 0 ? "+" : "-"}{attack.hit_dc} Attack</td>
                                        <td className="pc-form__combat-table-item">{attack.damage.diceNumber}d{attack.damage.diceType} {attack.damage.type}</td>
                                        <td className="pc-form__combat-table-item"><button></button></td>
                                    </tr>
                                ))}
                            </tbody>
                        </table>
                    </div>
                    <div className="pc-form__combat-item" id="pc-form__masteries">
                        <table>
                            <thead>
                                <th>Mastery Name</th>
                                <th>Property</th>
                                <th>Source</th>
                                <th>Controlls</th>
                            </thead>
                            <tbody>
                                {playerCharacter.combat && 
                                playerCharacter.combat.masteries && 
                                playerCharacter.combat.masteries.length > 0 && 
                                playerCharacter.combat.masteries.map(mastery => (
                                    <tr>
                                        <td>{mastery.name}</td>
                                        <td>{mastery.items}</td>
                                        <td>{mastery.description}</td>
                                        <td><button></button></td>
                                    </tr>
                                ))}
                            </tbody>
                        </table>
                    </div>
                    <div className="pc-form__combat-item" id="pc-form__effects">
                        <table>
                            <thead>
                                <th>Effect Name</th>
                                <th>Mod</th>
                                <th>Affects</th>
                                <th>Controlls</th>
                            </thead>
                            <tbody>
                                {playerCharacter.combat && 
                                playerCharacter.combat.effects && 
                                playerCharacter.combat.effects.length > 0 && 
                                playerCharacter.combat.effects.map(effect => (
                                    <tr>
                                        <td>{effect.name}</td>
                                        <td>{effect.diceValue} + {effect.otherBonus}</td>
                                        <td>{effect.applyEffectTo}</td>
                                        <td><button></button></td>
                                    </tr>
                                ))}
                            </tbody>
                        </table>
                    </div>
                    <div className="pc-form__combat-item" id="pc-form__actions">
                        <table>
                            <thead>
                                <th>Action Name</th>
                                <th>Details</th>
                                <th>Controlls</th>
                            </thead>
                            <tbody>
                                {playerCharacter.combat && 
                                playerCharacter.combat.actions && 
                                playerCharacter.combat.actions.length > 0 && 
                                playerCharacter.combat.actions.filter(a => a.type === "action").map(action => (
                                    <tr>
                                        <td>{action.name}</td>
                                        <td>{action.description}</td>
                                        <td><button></button></td>
                                    </tr>
                                ))}
                            </tbody>
                        </table>   
                    </div>
                    <div className="pc-form__combat-item" id="pc-form__bonus-actions">
                        <table>
                            <thead>
                                <th>Bonus Action Name</th>
                                <th>Details</th>
                                <th>Controlls</th>
                            </thead>
                            <tbody>
                                {playerCharacter.combat && 
                                playerCharacter.combat.actions && 
                                playerCharacter.combat.actions.length > 0 && 
                                playerCharacter.combat.actions.filter(a => a.type === "bonusAction").map(action => (
                                    <tr>
                                        <td>{action.name}</td>
                                        <td>{action.description}</td>
                                        <td><button></button></td>
                                    </tr>
                                ))}
                            </tbody>
                        </table>   
                    </div>
                    <div className="pc-form__combat-item" id="pc-form__reactions">
                        <table>
                            <thead>
                                <th>Reaction Name</th>
                                <th>Details</th>
                                <th>Controlls</th>
                            </thead>
                            <tbody>
                                {playerCharacter.combat && 
                                playerCharacter.combat.actions && 
                                playerCharacter.combat.actions.length > 0 && 
                                playerCharacter.combat.actions.filter(a => a.type === "reaction").map(action => (
                                    <tr>
                                        <td>{action.name}</td>
                                        <td>{action.description}</td>
                                        <td><button></button></td>
                                    </tr>
                                ))}
                            </tbody>
                        </table>   
                    </div>
                    <div className="pc-form__combat-item" id="pc-form__free-actions">
                        <table>
                            <thead>
                                <th>Free Action Name</th>
                                <th>Details</th>
                                <th>Controlls</th>
                            </thead>
                            <tbody>
                                {playerCharacter.combat && 
                                playerCharacter.combat.actions && 
                                playerCharacter.combat.actions.length > 0 && 
                                playerCharacter.combat.actions.filter(a => a.type === "free_action").map(action => (
                                    <tr>
                                        <td>{action.name}</td>
                                        <td>{action.description}</td>
                                        <td><button></button></td>
                                    </tr>
                                ))}
                            </tbody>
                        </table>   
                    </div>
                </div>
            )}
            {selectedDetail === "spells" && (
                <div className="pc-form__spells"></div>
            )}
            {selectedDetail === "inventory" && (
                <div className="pc-form__inventory">
                    <div className="pc-form__money">
                        <div className="pc-form__money-item">Platinum: {playerCharacter.inventory.coins.platinum}</div>
                        <div className="pc-form__money-item">Gold: {playerCharacter.inventory.coins.gold}</div>
                        <div className="pc-form__money-item">Electrum: {playerCharacter.inventory.coins.electrum}</div>
                        <div className="pc-form__money-item">Silver: {playerCharacter.inventory.coins.silver}</div>
                        <div className="pc-form__money-item">Copper: {playerCharacter.inventory.coins.copper}</div>
                    </div>
                    <div className="pc-form__item-search">
                        <input type="text" className="pc-form__item-search-input"/>
                        <button className="pc-form__item-add-button">Add item</button>
                    </div>
                    <div className="pc-form__total-weight">{playerCharacter.inventory.totalWeight}</div>
                    <div className="pc-form__equipment">
                        <div className="pc-form__equipment-item">
                            <table>
                                <thead>
                                    <th>Equipment</th>
                                    <th>Weight</th>
                                    <th>Quantity</th>
                                    <th>Controlls</th>
                                </thead>
                                <tbody>
                                    {playerCharacter.inventory.equipment.map(item => (
                                        <tr>
                                            <td>{item.name}</td>
                                            <td>{item.weight} lb</td>
                                            <td>{item.quantity}</td>
                                        </tr>
                                    ))}
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            )}
            {selectedDetail === "features_traits" && (
                <div className="pc-form__features-traits">
                    <div className="pc-form__features-search">

                    </div>
                    <div className="pc-form__class-features"></div>
                    <div className="pc-form__species-traits"></div>
                    <div className="pc-form__feats"></div>
                    <div className="pc-form__other"></div>
                </div>
            )}
            {selectedDetail === "notes" && (
                <div className="pc-form__notes">
                    <div className="pc-form__notes-search"></div>
                    <div className="pc-form__organizations"></div>
                    <div className="pc-form__allies"></div>
                    <div className="pc-form__enemies"></div>
                </div>
            )}
            {selectedDetail === "about" && (
                <div className="pc-form__about">
                    <div className="pc-form__about-search"></div>
                    <div className="pc-form__background">
                        <table>
                            <thead>
                                <th>Background</th>
                                <th></th>
                            </thead>
                            <tbody>
                                {playerCharacter.about.background.map(info => (
                                    <tr>
                                        <td>{info.name}</td>
                                        <td>{info.description}</td>
                                    </tr>
                                ))}
                            </tbody>
                        </table>
                    </div>
                    <div className="pc-form__characteristics"></div>
                    <div className="pc-form__appearance"></div>
                </div>
            )}    
        </div>
        
    )
}