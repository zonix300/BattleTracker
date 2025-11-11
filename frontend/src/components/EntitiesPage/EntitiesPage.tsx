import { useEffect, useState } from "react"
import { LeftSideBar } from "./components/LeftSideBar"
import { Main } from "./components/Main"
import { TopBar } from "./components/TopBar"
import "./EntitiesPage.css"
import { Entity } from "./type/Entity"
import { useFetchEntities } from "./hooks/useFetchEntities"
import { PlayerCharacter } from "./type/PlayerCharacter"

export const EntitiesPage = () => {
    const [entities, setEntities] = useState<PlayerCharacter[]>([]);
    const [selectedEntity, setSelectedEntity] = useState<PlayerCharacter | undefined>(undefined);

    const {fetchEntities, isFetchingEntities} = useFetchEntities({setEntities});

    useEffect(() => {
        fetchEntities();
    }, []);
    
    const onCardClick = (entity: PlayerCharacter) => {
        if (selectedEntity === entity) {
            setSelectedEntity(undefined);
        } else {
            setSelectedEntity(entity);
        }
    }

    return (
        <div className="entities-page__container">
            <TopBar />
            <Main entities={entities} setEntities={setEntities} selectedEntity={selectedEntity} onCardClick={onCardClick}/>
        </div>
    )
}