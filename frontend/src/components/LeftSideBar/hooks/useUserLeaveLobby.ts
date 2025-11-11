import { useCallback } from "react";
import { useSecureApiCall } from "../../../hooks/useSecureApiCall"
import { Lobby } from "../../Lobby/type/Lobby";

type handleUserLeaveLobbyProps = {
    lobbyId: number,
    setLobby: React.Dispatch<React.SetStateAction<Lobby | undefined>>

}

export const useUserLeaveLobby = () => {
    const {makeApiCall} = useSecureApiCall();

    const handleUserLeaveLobby = useCallback(({lobbyId, setLobby} : handleUserLeaveLobbyProps) => {

        makeApiCall(
            "delete",
            `/api/lobbies/${lobbyId}/leave`,
            null,
            {
                onSuccess: (data) => {
                    setLobby(data);
                },
                onError: (error) => {
                    console.error(error);
                }
            }
        )
    }, []); 

    return handleUserLeaveLobby;
}