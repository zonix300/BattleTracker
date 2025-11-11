import { useCallback } from "react";
import { useSecureApiCall } from "../../../hooks/useSecureApiCall"

export const useLobbyConnect = () => {
    const { makeApiCall, isLoading } = useSecureApiCall();


    const handleConnect = useCallback(async (lobbyId: number, password: string) => {
        makeApiCall(
            "put",
            `/api/lobbies/${lobbyId}/connect`,
            password,
            {
                showLoadingFor: `connecting-to-lobby-${lobbyId}`,
                onError: (error) => {
                    console.error("Error connecting user to lobby: ", lobbyId, error);
                },
                onSuccess: () => {
                    console.log("Successfully connected user to lobby: ", lobbyId);
                }
            }
        );
    }, []);

    return {
        handleConnect
    }
}