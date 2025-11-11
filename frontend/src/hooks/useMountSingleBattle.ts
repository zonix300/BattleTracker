import { useCallback } from "react"
import { Lobby } from "../components/Lobby/type/Lobby"
import { Combat } from "../components/type/Combat"
import { useSecureApiCall } from "./useSecureApiCall"

type useMountProps = {
    setCombat: React.Dispatch<React.SetStateAction<Combat | undefined>>
}

export const useMountSingleBattle = ({setCombat} : useMountProps) => {
    const {makeApiCall, isLoading} = useSecureApiCall();

    const mountSingle = useCallback(async () => {
        makeApiCall(
            "get",
            "/api/battles",
            {},
            {
                onSuccess: (data) => {
                    console.log(data);
                    setCombat(data);
                }
            }

        )
    }, []);

    return mountSingle;
}