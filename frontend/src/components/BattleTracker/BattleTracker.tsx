import {useEffect, useState} from "react";
import CombatantsTable from "../CombatantsTable/CombatantsTable";
import CreatureSheet from "../CreatureSheet/CreatureSheet";
import { Combatant } from "../type/Combatant";
import "./BattleTracker.css"
import { Combat } from "../type/Combat";
import { useModal } from "../../hooks/useModal";
import { useAnimatedModal } from "./Modal/Modal";
import { AuthManager } from "../Auth/AuthManager";
import { useGuestInit } from "../../hooks/useGuestInit";
import { useNavigate, useParams } from "react-router-dom";
import { Lobby } from "../Lobby/type/Lobby";
import { useMountLobbyBattle } from "../../hooks/useMountLobbyBattle";
import { useMountSingleBattle } from "../../hooks/useMountSingleBattle";
import { SecureWebSocket } from "../type/SecureWebSocket";
import { toast } from "react-toastify";
import { useLobbyMessage } from "../../hooks/useLobbyMessages";
import { useSideBar } from "../SideBar/SideBarContext";
import { LobbyInfoSideBar } from "../LeftSideBar/LobbyInfoSideBar/LobbyInfoSideBar";
import { useAuth } from "../Auth/AuthContext";
import { SearchSideBar } from "../LeftSideBar/SearchSideBar/SearchSideBar";

type BattleTrackerProps = {
    authManager: AuthManager,
    webSocket: SecureWebSocket
}

export const BattleTracker = ({authManager, webSocket} : BattleTrackerProps) => {
    const auth = useAuth();
    const [combat, setCombat] = useState<Combat | undefined>();
    const [lobby, setLobby] = useState<Lobby | undefined>();
    const [selectedCombatant, setSelectedCombatant] = useState<Combatant | null>(null);
    const [isSelectedCreatureLocked, setIsSelectedCreatureLocked] = useState(false);

    const { lobbyId } = useParams<{ lobbyId: string }>();
    const { addItem, removeItem } = useSideBar();

    const mountLobby = useMountLobbyBattle({setLobby, setCombat, addItem, webSocket, lobbyId});
    const mountSingle = useMountSingleBattle({setCombat});
    useLobbyMessage({ webSocket, lobbyId })

    const navigate = useNavigate();
    const modal = useModal();
    const handleGuestInit = useGuestInit();

    const handleGuestSuccess = async () => {
        handleGuestInit();
        modal.close();
    }

    const handleLoginRedirect = () => {
        navigate("/login");
    }

    const authModal = useAnimatedModal({
        isOpen: modal.isOpen,
        onClose: modal.close,
        title: "You are not authenticated!",
        showCloseButton: false,
        closeOnBackdropClick: false,
        children: (
            <div>
                <button onClick={handleLoginRedirect}>Login/Resigter</button>
                <button onClick={handleGuestSuccess}>Continue as Guest</button>
            </div>
        )
    });

    useEffect(() => {
        const checkAuthAndMount = async () => {
            auth?.checkAuthWithModal();

            if (!lobbyId) {
                console.log("Single");
                mountSingle();
            } else {
                console.log("Lobby");
                await mountLobby(); 
                await webSocket.waitForConnection();
                webSocket.sendMessage(
                    `/app/lobby/${lobbyId}/rejoin`,
                    null
                )
            }
        };

        checkAuthAndMount();

        return () => {
            removeItem("lobby-info");
            removeItem("bestiary");
        }

    }, [lobbyId, mountLobby, mountSingle, authManager]);

    useEffect(() => {
        console.log(combat);
        if (combat) {
            console.log("Combat name:", combat.name);
        }
        addItem({
            id: "bestiary",
            label: "Bestiary",
            onExpand: () => <SearchSideBar combat={combat} setCombat={setCombat}/>
        });
        
        if (!lobby || !lobbyId) return;

        addItem({
            id: "lobby-info",
            label: "Lobby Info",
            onExpand: () => <LobbyInfoSideBar lobby={lobby} setLobby={setLobby}/>
        });
        
    }, [lobby, combat])

    return (
        <div className="battle-tracker">
            <CombatantsTable
                combat={combat} 
                setCombat={setCombat}
                selectedCombatant={selectedCombatant}
                setSelectedCombatant={setSelectedCombatant}
                isSelectedCreatureLocked={isSelectedCreatureLocked}
            />
            <CreatureSheet
                selectedCombatant={selectedCombatant}
                isSelectedCreatureLocked={isSelectedCreatureLocked}
                setIsSelectedCreatureLocked={setIsSelectedCreatureLocked}
            />
            {authModal}
        </div>
    )
}