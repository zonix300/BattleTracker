import { useEffect } from "react";
import { SecureWebSocket } from "../components/type/SecureWebSocket";
import { Id, toast } from "react-toastify";
import { ConnectToast } from "../components/Toast/ConnectToast";

export type LobbyMessage = 
    | { id: number, type: "CONNECT"; initiator: {name: string, role: string} }
    | { type: "DISCONNECT"; user: string }
    | { type: "DAMAGE"; initiator: {name: string, role: string}, value: string, target: string }
    | { type: "HEAL"; initiator: {name: string, role: string}, value: string, target: string };

type UseLobbyMessageProps = {
    webSocket: SecureWebSocket;
    lobbyId: string | undefined;
};

export const useLobbyMessage = ({ webSocket, lobbyId }: UseLobbyMessageProps) => {
    useEffect(() => {
        if (!lobbyId) return;

        const handleLobbyMessage = (msg: any) => {
            const message: LobbyMessage = JSON.parse(msg.body);

            switch (message.type) {
                case "CONNECT":
                    showConnectToast(message.id, message.initiator);
                    break;
                case "DAMAGE":
                    showBattleToast(`${message.initiator} deals ${message.value} points of damage to ${message.target}`);
                    break;
            }
        };

        const subscribe = async () => {
            console.log("subscribe");
            await webSocket.waitForConnection();
            webSocket.subscribe(
                `/user/queue/lobby/${lobbyId}/requests`,
                handleLobbyMessage,
                { isUnique: true, groupKey: "/user/queue/lobby/" }
            );
        };

        subscribe();

        return () => {
            webSocket.unsubscribe("", "/user/queue/lobby/");
        };
    }, [lobbyId, webSocket]);

    const showConnectToast = (requestId: number, initiator: { name: string, role: string }) => {
        const toastId = toast.info(
            <ConnectToast
                username={initiator.name}
                role={initiator.role}
                onAccept={() => handleConnectionResponse(requestId, "ACCEPT", initiator.name, toastId)}
                onReject={() => handleConnectionResponse(requestId, "REJECT", initiator.name, toastId)}
            />,
            { autoClose: false }
        );
    };

    const handleConnectionResponse = (requestId: number, status: "ACCEPT" | "REJECT", user: string, toastId: Id) => {
        webSocket.sendMessage(`/app/lobby/${lobbyId}/request/${requestId}/decision`, { status });
        toast.dismiss(toastId);
    }

    const showBattleToast = (text: string) => {
        toast.info(text, { autoClose: 3000 });
    }
}

