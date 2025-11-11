import { useEffect, useState } from "react";
import { useFetchEntities } from "../hooks/useFetchEntities";
import { Entity } from "../type/Entity";
import { EntityCard } from "./EntityCard";
import { useNavigate } from "react-router-dom";
import { useDeleteEntity } from "../hooks/useDeleteEntity";
import { PlayerCharacter } from "../type/PlayerCharacter";

type MainProps = {
    entities: PlayerCharacter[];
    setEntities: React.Dispatch<React.SetStateAction<PlayerCharacter[]>>
    selectedEntity?: PlayerCharacter;
    onCardClick: (entity: PlayerCharacter) => void;
}

export const Main = ({entities, setEntities, selectedEntity, onCardClick}: MainProps) => {
    const navigate = useNavigate();
    const {deleteEntity, isDeletingEntity} = useDeleteEntity({setEntities});
    
    useEffect(() => {
        setEntities(entities);
    }, [entities]);

    const handleEdit = (entity: PlayerCharacter) => {
        navigate(`/pc-sheet/${entity.id}`);
    }

    const handleDelete = (entity: PlayerCharacter) => {
        deleteEntity(entity.id);
    }

    return (
        <div className="entities-main__container">
            <div className="entities-main__cards">
                <div className="entityes-main__header">
                    <h2 className="entities-main__header-title">Main</h2>
                    <p className="entities-main__header-subtitle">Your creatures/characters are stored here!</p>
                </div>
                {entities.length > 0 ? 
                    entities.map((entity) => (
                            <EntityCard 
                                key={entity.id}
                                entity={entity}
                                isSelected={selectedEntity === entity}
                                onClick={onCardClick}
                                onEdit={handleEdit}
                                onDelete={handleDelete}
                            />
                    )
                ) : (
                    <span>No entites yet!</span>
                )}
            </div>
        </div>
    )
}