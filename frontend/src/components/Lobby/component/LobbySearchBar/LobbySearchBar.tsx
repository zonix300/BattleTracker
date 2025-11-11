import { useEffect, useState } from "react"
import { useSecureApiCall } from "../../../../hooks/useSecureApiCall"
import { useSearch } from "../../hook/useSearch"
import { Lobby } from "../../type/Lobby"
import { useLobbyConnect } from "../../hook/useConnect"
import { useCreateLobby } from "../../hook/useCreateLobby"
import { useLobbySocket } from "../../../../hooks/useLobbySocket"
import { AuthManager } from "../../../Auth/AuthManager"
import { log } from "console"
import { SecureWebSocket } from "../../../type/SecureWebSocket"
import { IMessage, StompSubscription } from "@stomp/stompjs"
import { RequestCreateDTO } from "../../type/RequestCreateDTO"
import { useNavigate } from "react-router-dom"
import { Request } from "../../type/Request"

type LobbySearchBarProps = {
    authManager: AuthManager,
    selectedLobby: Lobby | undefined,
    setLobbies: React.Dispatch<React.SetStateAction<Lobby[] | undefined>>,
    webSocket: SecureWebSocket
}

export type LobbyCreateProps = {
    name: string,
    password: string
}

export const LobbySearchBar: React.FC<LobbySearchBarProps> = ({authManager, selectedLobby, setLobbies, webSocket}) => {

    const navigate = useNavigate();
    const [query, setQuery] = useState("");
    const [currentLobbyId, setCurrentLobbyId] = useState<number | undefined>(undefined);
    const [connectLobbyPassword, setConnectLobbyPassword] = useState("");
    const [lobbyCreateInfo, setLobbyCreateInfo] = useState<LobbyCreateProps>({
        name: "",
        password: ""
    });
    const {handleSearch, isSearching} = useSearch(setLobbies);
    const {handleCreateLobby, isCreating} = useCreateLobby({setCurrentLobbyId});

    const {makeApiCall, isLoading} = useSecureApiCall();

    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const { name, value } = e.target;
        setLobbyCreateInfo(prev => ({
            ...prev,
            [name]: value
        }));
    };

    const onSearchButtonClick = () => {
        handleSearch(query);
    }

    const onCreateButtonClick = async () => {
        selectedLobby = undefined;
        const createdLobbyId = await handleCreateLobby(lobbyCreateInfo);
        if (createdLobbyId) {
            webSocket.subscribe(
                `/user/queue/lobby/${createdLobbyId}/requests`,
                (msg) => {
                    console.log(msg);
                    const connection = JSON.parse(msg.body);
                    console.log("Message: " + connection);
                    console.log("Connection type: " + connection.type);
                },
                {
                    isUnique: true,
                    groupKey: "/user/queue/lobby/"
                }
            );
            navigate(`/lobby/${createdLobbyId}`);
        }
    }

    const onConnectButtonClick = () => {
        if (selectedLobby) {
            setCurrentLobbyId(selectedLobby.id);
            webSocket.subscribe(
                `/user/queue/lobby/${selectedLobby.id}/request/decision`, 
                (msg) => {
                    console.log("request decision mesasge", msg);
                    const payload = JSON.parse(msg.body);
                    console.log(payload);
                    if (payload.status === "ACCEPT") {
                        navigate(`/lobby/${selectedLobby?.id}`);
                    }
                },
                {
                    isUnique: true,
                    groupKey: "/topic/lobby/"
                }
            );

            const request: RequestCreateDTO = {
                type: "CONNECT"
            }
            webSocket.sendMessage(`/app/lobby/${selectedLobby.id}/requests`, request);
        }
    }

    return (
        <div className="lobby-search-bar__container">
            <div className="lobby-search-bar__header">
                <input
                    type="text"
                    value={query}
                    onChange={(e) => setQuery(e.target.value)}
                    placeholder="Search lobbies..."
                    className="lobby-search-bar__input"
                />
                <button className="lobby-search-bar__button" onClick={onSearchButtonClick} disabled={isSearching}>Find</button>
            </div>
            <div className="lobby-search-bar__creation-container">
                <input className="lobby-search-bar__input" type="text" name="name" placeholder="Name" onChange={handleChange}/>
                <input className="lobby-search-bar__input" type="password" name="password" placeholder="Password" onChange={handleChange}/>
                <button className="lobby-search-bar__button" onClick={onCreateButtonClick} disabled={isCreating}>Create</button>
            </div>
            
                
                {selectedLobby ? (
                    <div className="lobby-search-bar__selected-container">
                        <h2>Selected Lobby Players</h2>
                        <table className="lobby-search-bar__players-table">
                            <thead className="lobby-search-bar__players-header">
                                <tr>
                                    <th>Username</th>
                                    <th>Role</th>
                                </tr>
                            </thead>
                            <tbody className="lobby-search-bar__players-body">
                                {selectedLobby.players.map(player => (
                                    <tr
                                        key={player.name}
                                    >
                                        <td className="lobby-search-bar__player-name">{player.name}</td>
                                        <td className="lobby-search-bar__player-role">{player.role}</td>
                                    </tr>
                                ))}
                            </tbody>
                        </table>
                        {selectedLobby?.hasPassword ? (
                            <>
                                <span>Password</span>
                                <input className="lobby-search-bar__connect-lobby-password" onChange={(e) => setConnectLobbyPassword(e.target.value)}></input>
                            </>
                        ) : null}
                        {selectedLobby ? (
                            <button className="lobby-search-bar__button" onClick={onConnectButtonClick}>Connect</button>
                        ) : null }
                    </div>
                ) : null}
                
                
                
        </div>
    );
}