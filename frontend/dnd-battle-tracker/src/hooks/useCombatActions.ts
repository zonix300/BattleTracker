import { useCallback } from "react";
import { useSecureApiCall } from "./useSecureApiCall";
import { Combatant } from "../components/type/Combatant";
import { Combat } from "../components/type/Combat";

export const useCombatActions = (
    setCombat: React.Dispatch<React.SetStateAction<Combat | undefined>>,
    setCombatant: React.Dispatch<React.SetStateAction<Combatant | null>>,
    setCurrentCombatantId: React.Dispatch<React.SetStateAction<number | undefined>>
) => {
    const { makeApiCall, isLoading } = useSecureApiCall();

    const handleInitiativeRoll = useCallback(async (combatantId: number) => {
        await makeApiCall(
            "patch",
            `/api/combatants/${combatantId}/initiative-roll`,
            {},
            {
                showLoadingFor: `initiative-roll-${combatantId}`,
                validateInput: () => Number.isInteger(combatantId) && combatantId > 0,
                onSuccess: (combatant: Combatant) => {
                    setCombatant(combatant);
                }
            }
        );
    }, []);

    const handleDamage = useCallback(async (combatantId: number, amount: number) => {
        await makeApiCall(
            "patch",
            `/api/combatants/${combatantId}/damage`,
            {amount: amount},
            {
                showLoadingFor: `damaging-${combatantId}`,
                validateInput: () => Number.isInteger(combatantId) && combatantId > 0,
                onSuccess: (combat: Combat) => {
                    setCombat(combat);
                }
            }
        )
    }, []);

    const handleHeal = useCallback(async (combatantId: number, amount: number) => {
        await makeApiCall(
            "patch",
            `api/combatants/${combatantId}/heal`,
            {amount: amount},
            {
                showLoadingFor: `healing-${combatantId}`,
                validateInput: () => Number.isInteger(combatantId) && combatantId > 0,
                onSuccess: (combat: Combat) => {
                    setCombat(combat);
                }
            }
        )
    }, []);
    
    const handleEffects = () => {return} //todo

    const handleNextTurn = useCallback(async() => {
        await makeApiCall(
            "get",
            `api/battles/next-turn`,
            {},
            {
                showLoadingFor: "calculating-next-turn",
                onSuccess: (combat: Combat) => {
                    setCombat(combat);
                },
                onError: (error) => {
                    setCurrentCombatantId(undefined);
                    console.error("Error calculating next turn creature", error);
                }
            }
        )
    }, []);

    const handlePageReload = useCallback(async() => {
        await makeApiCall(
            "get",
            "/api/battles",
            {},
            {
                showLoadingFor: "fetching-combatants",
                onSuccess: (combat: Combat) => {
                    setCombat(combat);
                },
                onError: (error) => {
                    console.error("Failed to fetched combatants", error);
                }
            }
        )
    }, []);

    const handleDelete = useCallback(async(combatantId: number) => {
        await makeApiCall(
            "delete",
            `/api/combatants/${combatantId}`,
            {},
            {
                showLoadingFor: `deleting-combatant-${combatantId}`,
                onSuccess: (combat: Combat) => {
                    setCombat(combat);
                },
                onError: (error) => {
                    console.error("Failed to delete combatant", error);
                }
            }
        )
    }, []);

    return {
        handleInitiativeRoll,
        handleDamage,
        handleHeal,
        handleEffects,
        handleNextTurn,
        handlePageReload,
        handleDelete,
        isInitiativeRolling: (combatantId: number) => isLoading(`initiative-roll-${combatantId}`)
    };
};