import { useCallback } from "react";
import { useSecureApiCall } from "../../../hooks/useSecureApiCall";
import { Entity } from "../type/Entity";

type useFetchDraftsProps = {
    setDrafts: React.Dispatch<React.SetStateAction<Entity[]>>;
}

export const useFetchDrafts = ({setDrafts} : useFetchDraftsProps) => {
    const {makeApiCall, isLoading} = useSecureApiCall();

    const fetchDrafts = useCallback(() => {
        makeApiCall(
            "get",
            "/api/creatures/drafts",
            null,
            {
                showLoadingFor: "fetching-drafts",
                onSuccess: (data) => {
                    console.log("Drafts: ", data);
                    setDrafts(data);
                },
                onError: (error) => {
                    console.error(error);
                }
            }
        )
    }, []);

    return {
        fetchDrafts,
        isFetchingDrafts: () => isLoading("fetching-drafts")
    };
}