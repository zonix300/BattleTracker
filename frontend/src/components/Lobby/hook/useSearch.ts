import { useCallback } from "react";
import { useSecureApiCall } from "../../../hooks/useSecureApiCall";
import { Lobby } from "../type/Lobby";

export const useSearch = (
    setLobbies: React.Dispatch<React.SetStateAction<Lobby[] | undefined>>
) => {

    const {makeApiCall, isLoading} = useSecureApiCall();

    const handleSearch = useCallback(async (query: string) => {
        makeApiCall(
            "get",
            `/api/lobbies?query=${encodeURIComponent(query)}`,
            {},
            {
                showLoadingFor: "searching-for-lobbies",
                onSuccess: (data) => {
                    console.log(data.content);
                    setLobbies(data.content);
                },
                onError: (error) => {
                    console.log(error)
                }
            }
        )
    }, [])

    return {
        handleSearch,
        isSearching: isLoading("searching-for-lobbies")
    }
}