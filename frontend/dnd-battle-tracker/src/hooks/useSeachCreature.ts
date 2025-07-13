import { useCallback } from "react";
import { useSecureApiCall } from "./useSecureApiCall"
import { Creature } from "../components/type/Creature";

export const useSearchCreature = (
    templateId: number | null,
    setCreature: React.Dispatch<React.SetStateAction<Creature | null>>
) => {

    const {makeApiCall, isLoading} = useSecureApiCall();

    const handleSelectedCreatureChange = useCallback(async () => {
            if (templateId == null ) return;
            
            makeApiCall(
                "get",
                `/api/combatants/${templateId}`,
                {},
                {
                    showLoadingFor: `searching-for-creature-${templateId}`,
                    validateInput: () => {
                        return true;
                    },
                    onSuccess: (data) => {
                        setCreature(data);
                    },
                    onError: (error) => {
                        console.error(`Failed to load creature with ID: ${templateId}`, error);
                    }
                }
            )
        }, [makeApiCall, templateId]);

    return {
        handleSelectedCreatureChange,
        isSelectedCreatureChanging: (templateId: number) => isLoading(`searching-for-creature-${templateId}`)
    };
}