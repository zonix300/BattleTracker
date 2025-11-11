import { Client, StompSubscription } from "@stomp/stompjs"
import { AuthManager } from "../Auth/AuthManager";
import SockJS from "sockjs-client";

type Subscription = {
    destination: string,
    callback: (msg: any) => void,
    stompSub: StompSubscription;
};

export type SubscribeOptions = {
    isUnique?: boolean,
    groupKey?: string;
};

export class SecureWebSocket {
    private static instance: SecureWebSocket;
    private client: Client | null = null;
    private subscriptions: Subscription[] = [];
    private authManager: AuthManager;

    private constructor(authManager: AuthManager) {
        this.authManager = authManager;
    }

    public static getInstance(authManager: AuthManager) {
        if (!SecureWebSocket.instance) {
            SecureWebSocket.instance = new SecureWebSocket(authManager);
        }

        return SecureWebSocket.instance;
    }

    public connect() {
        if (this.client?.connected) return;
        if (this.authManager.getAuthState() === "UNAUTHENTICATED") {
            console.warn("Cannot connect, user not authenticated");
            return;
        }

        const stompClient = new Client({
            webSocketFactory: () => new SockJS("http://localhost:8080/ws"),
            connectHeaders: {
                Authorization: `Bearer ${this.authManager.getAuthToken()}`,
            },
            reconnectDelay: 5000,
            onConnect: () => {
                console.log("Websocket connected");

                this.subscriptions.forEach(sub => {
                    sub.stompSub.unsubscribe();
                    sub.stompSub = stompClient.subscribe(sub.destination, sub.callback);
                });
            },
            onStompError: (error) => console.error("STOMP error: ", error),
        });

        stompClient.activate();
        this.client = stompClient;
    }

    public disconnect() {
        if (this.client) {
            this.client.deactivate();
            this.client = null;
        }
    }

    public subscribe(destination: string, callback: (msg: any) => void, options?: SubscribeOptions) {
        if (!this.client?.connected) {
            console.warn("Cannot subscribe, client not connected yet");
            return;
        }

        if (options?.isUnique && options?.groupKey) {
            this.unsubscribe("", options.groupKey);
        }

        const stompSub = this.client.subscribe(destination, callback);
        this.subscriptions.push({ destination, callback, stompSub });
    }

    public unsubscribe(destination: string, groupKey?: string) {
        this.subscriptions = this.subscriptions.filter((sub) => {
            const matches = sub.destination === destination || (groupKey && sub.destination.startsWith(groupKey));
            if (matches) {
                sub.stompSub.unsubscribe();
                console.log("Unsubscribed from:", sub.destination);
            }
            return !matches;
        });
    }

    public sendMessage(destination: string, body: any) {
        if (!this.client?.connected) {
            console.warn("Cannot send message, client not connected");
            return;
        }

        this.client.publish({
            destination,
            body: typeof body === "string" ? body : JSON.stringify(body),
            headers: { Authorization: `Bearer ${this.authManager.getAuthToken()}` },
        });
    }

    public waitForConnection(): Promise<void> {
        return new Promise((resolve) => {
        if (this.client?.connected) {
            resolve();
            return;
        }
        const interval = setInterval(() => {
            if (this.client?.connected) {
            clearInterval(interval);
            resolve();
            }
        }, 50);
        });
    }
    
}