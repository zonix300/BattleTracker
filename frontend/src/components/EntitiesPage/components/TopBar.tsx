import { useState } from "react"
import { CreationDrawer } from "./CreationDrawer";

export const TopBar = () => {
    const [query, setQuery] = useState<string | undefined>(undefined);
    const [isDrawerOpen, setIsDrawerOpen] = useState(false);

    const switchDrawer = () => setIsDrawerOpen(!isDrawerOpen);
    const onDrawerClose = () => {
        setIsDrawerOpen(false);
    }

    return (
        <div className="entities-top-bar__container">
            <button 
                className="entities-top-bar__create-new-button"
                onClick={switchDrawer}
            >
                Create New
            </button>
            {isDrawerOpen && (
                <CreationDrawer
                    onClose={onDrawerClose}
                />
            )}
            <div className="entities-top-bar__filter-contorls">
                <input 
                    type="text"
                    className="entities-top-bar__filter-input"
                    value={query}
                    onChange={(e) => setQuery(e.target.value)}
                    placeholder="Find your creature..."
                />
                <span>Filter: </span>
                <button className="entities-top-bar__filter-button">All</button>
                <button className="entities-top-bar__filter-button">NPC</button>
                <button className="entities-top-bar__filter-button">PC</button>
                <button className="entities-top-bar__filter-button">Search</button>
            </div>
        </div>
    )
}