import { useCallback } from "react";
import { useSecureApiCall } from "./useSecureApiCall"
import { Credentials } from "../components/type/Credentials";
import { useNavigate } from "react-router-dom";

export const useLoginSubmit = () => {
    const {makeApiCall, isLoading} = useSecureApiCall();
    const navigate = useNavigate();

    const handleLoginSubmit = useCallback(async (credentials: Credentials) => {
        await makeApiCall(
            "post",
            "/api/auth/login",
            credentials,
            {
                showLoadingFor: "logging-in",
                skipAuth: true,
                validateInput: (data: Credentials) => {
                    return !!(data.email && data.password);
                },
                onSuccess: (data) => {
                    localStorage.setItem("token", data.token);
                    localStorage.setItem("user", JSON.stringify(data.userInfo));
                    navigate("/battle-tracker");
                },
                onError: (error) => {
                    console.error("Login failed: ", error);
                }
            }
        )
    }, [makeApiCall, navigate])

    return {
        handleLoginSubmit,
        isLoggingin: () => isLoading("logging-in")
    }
}