import React, { useCallback } from "react";
import { useSecureApiCall } from "./useSecureApiCall"
import { Combatant } from "../components/type/Combatant";
import { Pagination } from "../components/type/Pagination";
import { toast } from "react-toastify";

export const useSearch = (
    isSearchingCreatures: boolean,
    setCombatants: React.Dispatch<React.SetStateAction<Combatant[]>>,
    pagination: Pagination,
    setPagination: React.Dispatch<React.SetStateAction<Pagination>>
) => {
    
    const {makeApiCall, isLoading} = useSecureApiCall();
    
    const handleSearch = useCallback(async (query: string, page: number) => {
        makeApiCall(
            "post",
            isSearchingCreatures ? `api/combatants/search` : `/api/pc/search`,
            {
                query: query.toLocaleLowerCase().trim(),
                sortDirection: "ASC",
                page: page,
                size: pagination.size,
                sortBy: "name"
            },
            {
                showLoadingFor: `searching-for-${query}`,
                validateInput: (data) => {
                    const trimmed = query.trim();
                    return (
                        trimmed.length > 0 &&
                        trimmed.length <= 50 &&
                        /^[a-zA-Z0-9 _-]+$/.test(trimmed)
                    );
                },
                onSuccess: ({ content, page } : {content: Combatant[], page: Pagination}) => {
                    setCombatants(content);
                    setPagination(page);
                },
                onError: (error) => {
                    toast.error(error);
                }
            }
        );
    }, [makeApiCall, setCombatants, isSearchingCreatures]);

    return {
        handleSearch,
        isSearching: (query: string) => isLoading(`searching-for-${query}`)
    };
}