import { useCallback } from "react";
import { useSecureApiCall } from "./useSecureApiCall"
import { Credentials } from "../components/type/Credentials";
import { useNavigate } from "react-router-dom";

export const useRegisterSubmit = () => {
    const {makeApiCall, isLoading} = useSecureApiCall();
    const navigate = useNavigate();

    const handleRegisterSubmit = useCallback(async (credentials: Credentials) => {
        await makeApiCall(
            "post",
            "/api/auth/register",
            credentials,
            {
                showLoadingFor: "registering",
                skipAuth: true,
                validateInput: (data: Credentials) => {
                    return !!(data.username && data.password);
                },
                onSuccess: (data) => {
                    localStorage.setItem("token", data.token);
                    localStorage.setItem("user", JSON.stringify(data.userInfo));
                },
                onError: (error) => {
                    console.error("Register failed: ", error);
                }
            }
        )
    }, [makeApiCall, navigate])

    return {
        handleRegisterSubmit,
        isRegistering: () => isLoading("registering")
    }
}