import { useNavigate } from "react-router-dom";
import { Lobby } from "../../Lobby/type/Lobby";
import { useUserLeaveLobby } from "../hooks/useUserLeaveLobby";
import "./LobbyInfoSideBar.css"

type LobbyInfoSideBarProps = {
    lobby: Lobby | undefined,
    setLobby: React.Dispatch<React.SetStateAction<Lobby | undefined>>

}

export const LobbyInfoSideBar = ({lobby, setLobby} : LobbyInfoSideBarProps) => {

    const handleUserLeaveLobby = useUserLeaveLobby();
    const navigate = useNavigate();

    const handleLeaveButtonClick = () => {
        const lobbyId = lobby?.id;
        if (lobbyId) {
            handleUserLeaveLobby({ lobbyId, setLobby });
            navigate("/home");
        } else {
            console.error("Can't leave from non existing lobby");
        }

    }

    return (
        <div className="lobby-info-side-bar__container">
            <div className="lobby-info-side-bar__header">
                <div className="lobby-info-side-bar__title">
                    {
                        lobby?.name
                    }
                </div>
            </div>
            <div className="lobby-info-side-bar__body">
                <div className="lobby-info-side-bar__players-data">
                    {
                        lobby?.players.map((player) => (
                            <div key={player.name} className="lobby-info-side-bar__player-item">
                                <span>{player.name} {player.role}</span>
                            </div>
                        ))
                    }
                </div>
            </div>
            <div className="lobby-info-side-bar__footer">
                <div className="lobby-info-side-bar__controls">
                    <button className="lobby-info-side-bar__leave" onClick={handleLeaveButtonClick}>LEAVE</button>
                </div>
            </div>
        </div>
    );
}