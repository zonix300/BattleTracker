import { useCallback } from "react";
import { useSecureApiCall } from "./useSecureApiCall"
import { Creature } from "../components/type/Creature";
import { Combatant } from "../components/type/Combatant";

export const useSearchCreature = (
    selectedCombatant: Combatant | null,
    setCreature: React.Dispatch<React.SetStateAction<Creature | null>>
) => {

    const {makeApiCall, isLoading} = useSecureApiCall();

    const handleSelectedCreatureChange = useCallback(async () => {
            if (selectedCombatant === null ) return;
            
            makeApiCall(
                "get",
                `/api/combatants/${selectedCombatant.id}`,
                {},
                {
                    showLoadingFor: `searching-for-creature-${selectedCombatant.templateCreatureId}`,
                    validateInput: () => {
                        return true;
                    },
                    onSuccess: (data) => {
                        setCreature(data);
                    },
                    onError: (error) => {
                        console.error(`Failed to load creature with ID: ${selectedCombatant.templateCreatureId}`, error);
                    }
                }
            )          
        }, [makeApiCall, selectedCombatant]);

    return {
        handleSelectedCreatureChange,
        isSelectedCreatureChanging: (templateId: number) => isLoading(`searching-for-creature-${templateId}`)
    };
}