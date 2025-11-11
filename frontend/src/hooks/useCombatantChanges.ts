import { useCallback } from "react";
import { useSecureApiCall } from "./useSecureApiCall"
import { Combatant } from "../components/type/Combatant";
import { Combat } from "../components/type/Combat";

export const useCombatantChanges = (
    setCombat: React.Dispatch<React.SetStateAction<Combat | undefined>>,
    setChangingId: React.Dispatch<React.SetStateAction<number | undefined>>,
    setEditedCombatant: React.Dispatch<React.SetStateAction<Combatant | null>>
) => {

    const {makeApiCall, isLoading} = useSecureApiCall();

    const handleCombatantChanges = useCallback(async (combatant: Combatant | null) => {
        if (combatant) {
            await makeApiCall(
                "put",
                `api/combatants/${combatant.id}`,
                {
                    initiative: combatant.initiative,
                    name: combatant.name,
                    currentHp: combatant.currentHp,
                    maxHp: combatant.maxHp,
                    armorClass: combatant.armorClass
                },
                {
                    showLoadingFor: `changing-combatant-${combatant.id}`,
                    validateInput: () => {
                        return true;
                    },
                    onSuccess: (combat: Combat) => {
                        setChangingId(undefined);
                        setEditedCombatant(null);
                        setCombat(combat);
                    },
                    onError: (error) => {
                        console.error(`Changing combatant with ID: ${combatant.id} failed`, error);
                    }
                }
            );
        }
    }, [makeApiCall]);
    

    return {
        handleCombatantChanges,
        isCombatantChanging: (combatantId: number) => isLoading(`changing-combatant-${combatantId}`)
    };
}