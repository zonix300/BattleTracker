import { useCallback } from "react";
import { Combatant } from "../components/type/Combatant";
import { useSecureApiCall } from "./useSecureApiCall";
import { Combat } from "../components/type/Combat";

export const useAddCombatant = (
    combat: Combat | undefined,
    setCombat: React.Dispatch<React.SetStateAction<Combat | undefined>>
) => {

    const {makeApiCall, isLoading} = useSecureApiCall();
    
    const handleAddCombatant = useCallback(async (templateId: number | null) => {
        await makeApiCall(
            "get",
            `/api/combatants/${templateId}/add`,
            {},
            {
                showLoadingFor: `adding-combatant-by-${templateId}`,
                validateInput: (data) => {
                    return typeof templateId === "number" && templateId > 0;
                },
                onSuccess: ((combat : Combat) => {
                    setCombat(combat);
                })
            }
        );
    }, []);

    return {
        handleAddCombatant,
        isAdding: (templateId : number | null) => isLoading(`adding-combatant-by-${templateId}`)
    };
}