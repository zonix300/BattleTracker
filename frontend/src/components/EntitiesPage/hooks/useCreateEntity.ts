import { useCallback } from "react";
import { useSecureApiCall } from "../../../hooks/useSecureApiCall"
import { data } from "react-router-dom";
import { error } from "console";

export const useCreateEntity = () => {
    const {makeApiCall, isLoading} = useSecureApiCall();

    const handlePCOptionClick = useCallback((setId: React.Dispatch<React.SetStateAction<number>>) => {
        makeApiCall(
            "post",
            "/api/pc",
            null,
            {
                showLoadingFor: "creating-entity",
                onSuccess: (data) => {
                    console.log(data);
                    setId(data);
                },
                onError: (error) => {
                    console.error(error);
                }
            }
        )
    }, []);

    return {
        handlePCOptionClick,
        isCreating: () => isLoading("creating-entity")
    }
}