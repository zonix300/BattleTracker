import { Client, IFrame, Stomp } from "@stomp/stompjs"
import { useCallback, useEffect, useRef, useState } from "react"
import SockJS from "sockjs-client";
import { AuthManager } from "../components/Auth/AuthManager";
import { useNavigate } from "react-router-dom";

export const useLobbySocket = (authManager: AuthManager) => {

    const [client, setClient] = useState<Client | null>(null);
    const connect = useCallback((lobbyId: number | undefined) => {
        const stompClient = new Client({
            webSocketFactory: () => new SockJS('http://localhost:8080/ws'),
            connectHeaders: {
                Authorization: `Bearer ${authManager.getAuthToken()}`
            },
            onConnect: (frame) => {
                console.log("Connected successfully: ", frame);
                console.log("Lobby id: ", lobbyId);

                stompClient.subscribe(`/topic/lobby/${lobbyId}`, (message) => {
                    console.log("Received message:", JSON.parse(message.body));
                });
                stompClient.publish({
                    destination: `/app/lobby/${lobbyId}`,
                    headers: {
                        Authorization: `Bearer ${authManager.getAuthToken()}`
                    },
                    body: JSON.stringify({message: "hello"})
                });
            },
            onStompError: (frame) => {
                console.error("STOMP Error:", frame.headers["message"]);
                console.error("Details:", frame.body);
            }
        });

        stompClient.activate();
        setClient(stompClient);
        
        return () => {
                if (stompClient.active) {
                    stompClient.deactivate();
                }
            };
    }, [authManager]);
    

    return {client, connect};
}