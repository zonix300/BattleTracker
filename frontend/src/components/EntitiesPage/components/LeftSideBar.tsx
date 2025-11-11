import { useEffect, useState } from "react"
import { useFetchDrafts } from "../hooks/useFetchDrafts";
import { Entity } from "../type/Entity";
import "./EntityCard.css";

type LeftSideBarProps = {
    entities: Entity[];
    selectedEntity?: Entity;
    onCardClick: (entity: Entity) => void;
}

export const LeftSideBar = ({entities, selectedEntity, onCardClick} : LeftSideBarProps) => {
    const [drafts, setDrafts] = useState<Entity[]>([]);

    useEffect(() => {
        setDrafts(entities.filter(e => e.draft === true));
    }, [entities]);

    return (
        <div className="entity-left__container">
            <div className="entity-left__header">
                <div className="entity-left__header">In Progress</div>
                <div className="entity-left__subheader">Your unfinished creatures/characters are stored here!</div>
            </div>
            <div className="entity-left__drafts">
                {drafts.length > 0 ? 
                    drafts.map((draft) => (
                        <div 
                            key={draft.id}
                            className={`entity-left__card${selectedEntity===draft ? "--selected" : ""}`}
                            onClick={() => onCardClick(draft)}
                        >
                            <div className="entity-left__card-header">{draft.name}</div>
                            <div className="entity-left__card-body">{draft.type}</div>
                            <div className="entity-left__card-footer">
                                <div className="entity-left__card-controlls">
                                    <button className="enitity-main__card-edit-button">Edit</button>
                                    <button className="enttity-main__card-delete-button">Delete</button>
                                </div>
                            </div>
                        </div>
                    )
                ) : (
                    <div className="entity-left__card">
                        <div className="entity-left__card-header"></div>
                        <div className="entity-left__card-body"></div>
                        <div className="entity-left__card-footer"></div>
                    </div>
                )}
            </div>
        </div>
    )
}