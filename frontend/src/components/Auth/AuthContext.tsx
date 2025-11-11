import { createContext, ReactNode, useContext, useEffect, useState } from "react";
import { AuthManager } from "./AuthManager";
import { useModal } from "../../hooks/useModal";
import { useNavigate } from "react-router-dom";
import { useAnimatedModal } from "../BattleTracker/Modal/Modal";
import { toast } from "react-toastify";
import { Login } from "./AuthView/Login/Login";
import { AuthModal } from "./AuthView/AuthModal";

type AuthContextType = {
    authManager: AuthManager,
    authState: "AUTHENTICATED" | "UNAUTHENTICATED",
    getToken: () => string | null,
    getRole: () => "USER" | "GUEST" | null,
    checkAuth: () => boolean,
    checkAuthWithModal: () => boolean,
    openAuthModal: () => void,
    logout: () => void;
};

const AuthContext = createContext<AuthContextType | null>(null);

export const AuthProvider = ({children} : {children: ReactNode}) => {
    const [authManager] = useState(() => new AuthManager());
    const [authState] = useState(authManager.getAuthState());

    const modal = useModal();

    const authModal = useAnimatedModal({
        isOpen: modal.isOpen,
        onClose: modal.close,
        children: <AuthModal modal={modal}/>,
        title: "You are not authenticated!"
    });

    const getToken = () => {
        const token = authManager.getAuthToken();
        if (!token) {
            modal.open()
            return null;
        }
        return token;
    }

    const getRole = () => {
        const role = authManager.getRole();

        if (role === undefined) {
            modal.open();
            return null;
        }
        return role;
    }

    const checkAuth = () => {
        const token = authManager.getAuthState();

        return token && !authManager.isTokenExpired() && authState === "AUTHENTICATED";
    }

    const checkAuthWithModal = () => {
        const auth = checkAuth();
        if (!auth) {
            modal.open()
        }
        return auth;
    }

    const logout = () => {
        authManager.clearAuthData();
    }

    const openAuthModal = () => modal.open();

    return (
        <AuthContext.Provider value={{ 
                authManager, 
                authState, 
                getToken, 
                getRole,
                checkAuth,
                checkAuthWithModal,
                openAuthModal,
                logout
            }}>
            {children}
            {authModal}
        </AuthContext.Provider>
    )
}

export const useAuth = () => useContext(AuthContext);