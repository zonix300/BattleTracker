import { useNavigate } from "react-router-dom"
import { useCreateEntity } from "../hooks/useCreateEntity";
import { useEffect, useState } from "react";

type Props = {
    onClose: () => void;
}

export const CreationDrawer = ({onClose} : Props) => {
    const navigate = useNavigate();
    const [id, setId] = useState<number>(-1);
    const {handlePCOptionClick, isCreating} = useCreateEntity();
    const onPCOptionClick = () => {
        handlePCOptionClick(setId);
    }

    const onNPCOptionClick = () => {
        navigate("/npc-sheet");
    }

    useEffect(() => {
        if (id > 0) {
            navigate(`/pc-sheet/${id}`);
        }
    }, [id]);
    
    return (
        <div className="creation-drawer__backdrop">
            <div
                className="creation-drawer__panel"
                onClick={(e) => e.stopPropagation()}
            >
                <div className="creation-drawer__header">
                    <div>
                        <h2>Create New Character, Monster or NPC</h2>
                        <p>Choose what kind of creature you want to create</p>
                    </div>
                    <button 
                        className="creation-drawer__close-button"
                        onClick={onClose}
                    >x</button>
                </div>

                <div className="creation-drawer__options">
                    <button 
                        className="creation-drawer__option"
                        onClick={onPCOptionClick}
                    >
                        Character Sheet
                    </button>
                    <button 
                        className="creation-drawer__option"
                        onClick={onNPCOptionClick}
                    >
                        Monster/NPC Sheet
                    </button>
                </div>
            </div>
        </div>
    )
}