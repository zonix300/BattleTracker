import { useCallback } from "react";
import { useSecureApiCall } from "../../../hooks/useSecureApiCall"
import { LobbyCreateProps } from "../component/LobbySearchBar/LobbySearchBar";
import { Client } from "@stomp/stompjs";
import { resolve } from "node:dns/promises";
import { rejects } from "node:assert";

type createLobbyProps = {
    setCurrentLobbyId: React.Dispatch<React.SetStateAction<number | undefined>>
}

export const useCreateLobby = ({setCurrentLobbyId}: createLobbyProps) => {
    const { makeApiCall, isLoading } = useSecureApiCall();

    const handleCreateLobby = useCallback( async(request: LobbyCreateProps): Promise<number | null> => {
        return new Promise((resolve, reject) => {
            makeApiCall(
                "post",
                "/api/lobbies",
                request,
                {
                    showLoadingFor: "creating-lobby",
                    onSuccess: (data) => {
                        const newId = Number(data.id);
                        setCurrentLobbyId(newId);
                        resolve(newId);
                    },
                    onError: (error) => {
                        console.error("Error creating lobby", error);
                        reject(error);
                    }
                }
            );
        });

    }, []);

    return {
        handleCreateLobby,
        isCreating: isLoading("creating-lobby")
    }
}