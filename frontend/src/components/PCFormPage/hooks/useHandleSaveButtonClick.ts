import { useCallback } from "react"
import { PlayerCharacter } from "../types/PlayerCharacter"
import { useSecureApiCall } from "../../../hooks/useSecureApiCall"

type useHandleSaveButtonClickProps = {
    playerCharacter: PlayerCharacter | null
}

export const useHandleSaveButtonClick = () => {
    
    const {makeApiCall, isLoading} = useSecureApiCall();
    const handleSaveButtonClick = useCallback(({playerCharacter} : useHandleSaveButtonClickProps) => {
        if (!playerCharacter) {
            throw new Error("No player character info");
        }
        makeApiCall(
            "put",
            `/api/pc/${playerCharacter?.id}`,
            playerCharacter,
            {
                showLoadingFor: "is-saving",
                onSuccess: (data) => {
                    console.log(data);
                },
                onError: (error) => {
                    console.error(error);
                }
            }
        )
    }, [])

    return handleSaveButtonClick;
}