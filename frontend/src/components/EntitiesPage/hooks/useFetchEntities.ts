import { useCallback } from "react";
import { useSecureApiCall } from "../../../hooks/useSecureApiCall"
import { Entity } from "../type/Entity";
import { PlayerCharacter } from "../type/PlayerCharacter";
type useFetchEntitiesProps = {
    setEntities: React.Dispatch<React.SetStateAction<PlayerCharacter[]>>;
}

export const useFetchEntities = ({setEntities} : useFetchEntitiesProps) => {
    const {makeApiCall, isLoading} = useSecureApiCall();

    const fetchEntities = useCallback(() => {
        makeApiCall(
            "get",
            "/api/pc",
            null,
            {
                showLoadingFor: "fetching-creatures",
                onSuccess: (data) => {
                    console.log("Entities: ", data);
                    setEntities(data);
                },
                onError: (error) => {
                    console.error(error);
                }
            }
        )
    }, []);

    return {
        fetchEntities,
        isFetchingEntities: () => isLoading("fetching-creatures")
    }
}