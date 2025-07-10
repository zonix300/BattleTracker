import { useCallback } from "react";
import { useSecureApiCall } from "./useSecureApiCall";
import { Combatant } from "../components/type/Combatant";

export const useCombatActions = (setCombatants: (fn: (prev: Combatant[]) => Combatant[]) => void) => {
    const { makeApiCall, isLoading } = useSecureApiCall();

    const handleInitiativeRoll = useCallback(async (combatantId: number) => {
        await makeApiCall(
            "patch",
            `/api/combatants/${combatantId}/initiative-roll`,
            {},
            {
                showLoadingFor: `initiative-roll-${combatantId}`,
                validateInput: () => Number.isInteger(combatantId) && combatantId > 0,
                onSuccess: (updatedCombatant: Combatant) => {
                    setCombatants(prev => 
                        prev.map(c => c.id === updatedCombatant.id ? updatedCombatant : c)
                    );
                }
            }
        );
    }, [makeApiCall, setCombatants]);

    const handleInitiativeChange = useCallback(async (combatantId: number, newInitiative: number) => {
        await makeApiCall(
            "patch",
            `/api/combatants/${combatantId}/initiative`,
            {initiative: newInitiative},
            {
                showLoadingFor: `initiative-change-${combatantId}`,
                validateInput: (data) => {
                    return Number.isInteger(combatantId) && combatantId > 0 &&
                           typeof data.initiative === "number" &&
                           Number.isFinite(data.initiative) &&
                           data.initiative >= -10 && data.initiative <= 50; 
                },
                onSuccess: (updatedCombatant: Combatant) => {
                    setCombatants(prev => 
                        prev.map(c => c.id === updatedCombatant.id ? updatedCombatant : c)
                    );
                } 
            }
        );
    }, [makeApiCall, setCombatants]);

    return {
        handleInitiativeRoll,
        handleInitiativeChange,
        isRollingInitiative: (id: number) => isLoading(`initiative-roll-${id}`),
        isChangingInitiative: (id: number) => isLoading(`initiative-change-${id}`)
    };
};