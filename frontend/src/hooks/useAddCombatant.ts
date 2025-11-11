import { useCallback } from "react";
import { Combatant } from "../components/type/Combatant";
import { useSecureApiCall } from "./useSecureApiCall";
import { Combat } from "../components/type/Combat";

type useAddCombatantProps = {
    combat: Combat | undefined,
    setCombat: React.Dispatch<React.SetStateAction<Combat | undefined>>
}

export const useAddCombatant = ({ combat, setCombat } : useAddCombatantProps) => {

    const {makeApiCall, isLoading} = useSecureApiCall();
    
    const handleAddCombatant = useCallback(async (combatant: Combatant) => {
        if (combatant.type === "PLAYER_CHARACTER") {
            await makeApiCall(
                "patch",
                combat ? `/api/pc/${combatant.id}/add?battleId=${combat.id}` : "bad-add-combatant-request",
                null,
                {
                    onSuccess: (combat: Combat) => {
                        setCombat(combat);
                    }
                }
            )
        } else {
            await makeApiCall(
                "get",
                `/api/combatants/${combatant.id}/add`,
                null,
                {
                    showLoadingFor: `adding-combatant-by-${combatant.id}`,
                    validateInput: (data) => {
                        return typeof combatant.id === "number" && combatant.id > 0;
                    },
                    onSuccess: ((combat : Combat) => {
                        setCombat(combat);
                    })
                }
            );
        }
    }, []);

    return {
        handleAddCombatant,
        isAdding: (templateId : number | null) => isLoading(`adding-combatant-by-${templateId}`)
    };
}