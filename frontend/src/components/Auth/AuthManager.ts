import { useNavigate } from "react-router-dom";
import { useAnimatedModal } from "../BattleTracker/Modal/Modal";
import { useModal } from "../../hooks/useModal";

type User = {
    token: string;
    username: string;
    role: "USER" | "GUEST";
    expiresAt: Date;
}

export class AuthManager {

    private user: User | null = null;
    private authState: "AUTHENTICATED" | "UNAUTHENTICATED" = "UNAUTHENTICATED";

    constructor() {
        this.initializeFromStorage();
    }

    private initializeFromStorage(): void {
        const userJSON = localStorage.getItem("user");
        const token = localStorage.getItem("token");

        if (userJSON && token) {
            try {
                const userData = JSON.parse(userJSON);
                this.user = userData;
                if (!this.user) {
                    throw new Error("Failed to parse user data");
                }

                this.user.token = token;
                this.authState = "AUTHENTICATED";
                this.user.expiresAt = new Date(this.user.expiresAt);
               
            } catch (e) {
                console.error("Failed to parse user data:", e);
                localStorage.removeItem("user");
            }
        }
    }

    getAuthToken () {
        const token = localStorage.getItem("token");
        return token;
    }

    isTokenExpired(): boolean {
        this.initializeFromStorage();

        if (!this.user) return true;
        
        return this.user.expiresAt < new Date();
    }


    public getAuthState() {
        return this.authState;
    }

    async clearAuthData () {
        localStorage.removeItem("token");
        localStorage.removeItem("user");
        this.user = null;
        this.authState = "UNAUTHENTICATED";
    };

    public getUsername() {
        return this.user?.username;
    }

    public getRole() {
        return this.user?.role;
    }
}