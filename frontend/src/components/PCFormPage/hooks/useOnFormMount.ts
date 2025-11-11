import { useCallback } from "react";
import { useSecureApiCall } from "../../../hooks/useSecureApiCall"
import { PlayerCharacter } from "../types/PlayerCharacter";

type useOnFormMountProps = {
    setPlayerCharacter: React.Dispatch<React.SetStateAction<PlayerCharacter | null>>
}
export const useOnFormMount = ({setPlayerCharacter}: useOnFormMountProps) => {
    const {makeApiCall, isLoading} = useSecureApiCall();

    const onFormMount = useCallback((id: number) => {
        makeApiCall(
            "get",
            `/api/pc/${id}`,
            null,
            {
                showLoadingFor: "fetching-pc",
                onSuccess: (data) => {
                    console.log(data);
                    setPlayerCharacter(data);
                },
                onError: (error) => {
                    console.error(error);
                }
            }
        )
    }, []);

    return {
        onFormMount,
        isFetchingPlayerCharacter: () => isLoading("fetching-pc")
    };
}