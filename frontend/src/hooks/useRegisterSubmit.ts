import { useCallback } from "react";
import { useSecureApiCall } from "./useSecureApiCall"
import { Credentials } from "../components/type/Credentials";
import { useNavigate } from "react-router-dom";
import { Modal } from "./useModal";

export const useRegisterSubmit = (modal: Modal) => {
    const {makeApiCall, isLoading} = useSecureApiCall();

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
                    modal.close();
                },
                onError: (error) => {
                    console.error("Register failed: ", error);
                }
            }
        )
    }, [makeApiCall])

    return {
        handleRegisterSubmit,
        isRegistering: () => isLoading("registering")
    }
}