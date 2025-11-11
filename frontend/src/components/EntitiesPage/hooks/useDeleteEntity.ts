import { useCallback } from "react";
import { useSecureApiCall } from "../../../hooks/useSecureApiCall";
import { Entity } from "../type/Entity";
import { PlayerCharacter } from "../type/PlayerCharacter";

type DeleteEntityProps = {
    setEntities: React.Dispatch<React.SetStateAction<PlayerCharacter[]>>
}

export const useDeleteEntity = ({setEntities} : DeleteEntityProps) => {

    const {makeApiCall, isLoading} = useSecureApiCall();

    const deleteEntity = useCallback((id: number) => {
        makeApiCall(
            "delete",
            `/api/pc/${id}`,
            null,
            {
                showLoadingFor: "deleting-entity",
                onSuccess: (data) => {
                    setEntities(data);
                },
                onError: (error) => {
                    console.error(error);
                }
            }
        )
    }, []);

    return {
        deleteEntity,
        isDeletingEntity: isLoading("deleting-entity")
    };
}