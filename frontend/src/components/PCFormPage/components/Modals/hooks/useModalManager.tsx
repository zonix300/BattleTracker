import { DetailElement } from "../../../types/DetailElement";
import { PlayerCharacter } from "../../../types/PlayerCharacter";
import { ActionModal } from "../ActionModal";
import { AttackModal } from "../AttackModal";
import { EffectModal } from "../EffectModal";
import { MasteryModal } from "../MasteryModal";

type useModalManagerProps = {
    setPlayerCharacter: React.Dispatch<React.SetStateAction<PlayerCharacter | null>>,
    onClose: () => void
}

export const useModalMager = ({setPlayerCharacter, onClose} : useModalManagerProps) => {

    const manageModal = (item: DetailElement | null) => {
        switch(item) {
            case DetailElement.Attack:
                return <AttackModal 
                    setPlayerCharacter={setPlayerCharacter}
                    onClose={onClose}
                />;
            case DetailElement.Effect:
                return <EffectModal
                    setPlayerCharacter={setPlayerCharacter}
                    onClose={onClose}
                />;
            case DetailElement.Action:
                return <ActionModal
                    setPlayerCharacter={setPlayerCharacter}
                    onClose={onClose}
                />
            case DetailElement.Mastery:
                return <MasteryModal
                    setPlayerCharacter={setPlayerCharacter}
                    onClose={onClose}
                />

        }

        return null;
    }

    return manageModal
    
}