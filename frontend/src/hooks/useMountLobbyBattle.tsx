import { useCallback } from "react";
import { useSecureApiCall } from "./useSecureApiCall"
import { Lobby } from "../components/Lobby/type/Lobby";
import { Combat } from "../components/type/Combat";
import { AuthManager } from "../components/Auth/AuthManager";
import { SecureWebSocket } from "../components/type/SecureWebSocket";
import { SideBarItem } from "../components/SideBar/SideBarContext";
import { LobbyInfoSideBar } from "../components/LeftSideBar/LobbyInfoSideBar/LobbyInfoSideBar";
import { SearchSideBar } from "../components/LeftSideBar/SearchSideBar/SearchSideBar";

type useMountProps = {
    setLobby: React.Dispatch<React.SetStateAction<Lobby | undefined>>,
    setCombat: React.Dispatch<React.SetStateAction<Combat | undefined>>,
    addItem: (item: SideBarItem) => void
    webSocket: SecureWebSocket,
    lobbyId: string | undefined
}

export const useMountLobbyBattle = ({setLobby, setCombat, addItem, webSocket, lobbyId} : useMountProps) => {
    const { makeApiCall, isLoading } = useSecureApiCall();
    const mountLobby = useCallback(async() => {
        console.log("UseMountLobbyBattle ", lobbyId);   
        if (!lobbyId) return;

        makeApiCall(
            "get",
            `/api/lobbies/${lobbyId}/battle`,
            {},
            {
                onSuccess: async (data) => {
                    console.log("UseMountLobbyBattleData: ",data);
                    setLobby(data.lobbyResponse);
                    setCombat(data.battleResponse);
                    
                },
                onError: (error) => {
                    console.error("Failed to mount lobby", error);
                }
            }
        )
    }, [setLobby, setCombat, webSocket, makeApiCall]);

    return mountLobby;
}