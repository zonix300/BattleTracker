import { calcModifier } from "../../dndUtils";
import { Entity } from "../type/Entity";
import { PlayerCharacter } from "../type/PlayerCharacter";

type EntityCardProps = {
    entity: PlayerCharacter;
    isSelected?: boolean;
    onClick?: (entity: PlayerCharacter) => void;
    onEdit?: (entity: PlayerCharacter) => void;
    onDelete?: (entity: PlayerCharacter) => void;
};

export const EntityCard = ({ entity, isSelected, onClick, onEdit, onDelete }: EntityCardProps) => {

    return (
        <div 
            key={entity.id}
            className={`entity-main__card${isSelected ? "--selected" : ""}`}
            onClick={() => onClick?.(entity)}
        >
            <div className="entity-main__card-header">
                <p className="entity-main__card-header-first-line">
                    <span>{entity.name}</span>
                    <span>{entity.clazz.substring(0,1).toUpperCase() + entity.clazz.substring(1, entity.clazz.length)} {entity.level} lvl</span>
                </p>
                <p className="entity-main__card-header-second-line">
                    <span>HP: {entity.hp.current}/{entity.hp.max}</span>
                    <span>AC: {entity.armorClass}</span>
                    <span>Initiative: {calcModifier(entity.abilities.dexterity) >= 0 ? "+" + calcModifier(entity.abilities.dexterity) : calcModifier(entity.abilities.dexterity)}</span>
                </p>
                
            </div>
            <div className="entity-main__card-body"></div>
            <div className="entity-main__card-footer">
                <div className="entity-main__card-controlls">
                    <button onClick={(e) => { e.stopPropagation(); onEdit?.(entity); }}>Edit</button>
                    <button onClick={(e) => { e.stopPropagation(); onDelete?.(entity); }}>Delete</button>
                </div>
            </div>
        </div>
    );
};