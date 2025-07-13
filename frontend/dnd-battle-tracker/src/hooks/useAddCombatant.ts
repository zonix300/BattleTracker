import { useCallback } from "react";
import { Combatant } from "../components/type/Combatant";
import { useSecureApiCall } from "./useSecureApiCall";

export const useAddCombatants = (
    combatants: Combatant[],
    setCombatants: React.Dispatch<React.SetStateAction<Combatant[]>>
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
                onSuccess: ((combatant : Combatant) => {
                    setCombatants(prev => [...prev, combatant]);
                })
            }
        );
    }, [makeApiCall, setCombatants]);

    return {
        handleAddCombatant,
        isAdding: (templateId : number | null) => isLoading(`adding-combatant-by-${templateId}`)
    };
}