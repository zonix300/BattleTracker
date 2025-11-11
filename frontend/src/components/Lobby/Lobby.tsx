import { useEffect, useState } from "react";
import type { Lobby } from "./type/Lobby";
import { LobbySearchResults } from "./component/LobbySearchResults/LobbySearchResults";
import { LobbySearchBar } from "./component/LobbySearchBar/LobbySearchBar";
import { AuthManager } from "../Auth/AuthManager";
import { SecureWebSocket } from "../type/SecureWebSocket";
import "../Lobby/Lobby.css"
import { useSideBar } from "../SideBar/SideBarContext";

interface LobbyPageProps {
    authManager: AuthManager,
    webSocket: SecureWebSocket
}

export const LobbyPage = ({authManager, webSocket}: LobbyPageProps) => {
    const [lobbies, setLobbies] = useState<Lobby[]>();
    const [selectedLobby, setSelectedLobby] = useState<Lobby>();
    

    return (
        <div className="lobbies__container">
            <LobbySearchResults
                lobbies={lobbies}
                selectedLobby={selectedLobby}
                setSelectedLobby={setSelectedLobby}
            />
            <LobbySearchBar
                authManager={authManager}
                selectedLobby={selectedLobby}
                setLobbies={setLobbies}
                webSocket={webSocket}
            />
        </div>
    );
}