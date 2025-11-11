import { useCallback } from "react";
import { useSecureApiCall } from "./useSecureApiCall"
import { useNavigate } from "react-router-dom";
import { Modal } from "./useModal";

type Credentials = {
    identifier: string,
    password: string
}

export const useLoginSubmit = (modal: Modal) => {
    const {makeApiCall, isLoading} = useSecureApiCall();

    const handleLoginSubmit = useCallback(async (credentials: Credentials, setInvalid: React.Dispatch<React.SetStateAction<boolean>>) => {
        await makeApiCall(
            "post",
            "/api/auth/login",
            credentials,
            {
                showLoadingFor: "logging-in",
                skipAuth: true,
                validateInput: (data: Credentials) => {
                    return !!(data.identifier && data.password);
                },
                onSuccess: (data) => {
                    modal.close();
                    localStorage.setItem("token", data.token);
                    localStorage.setItem("user", JSON.stringify(data.userInfo));
                    setInvalid(false);
                    console.log("token: ", localStorage.getItem("token"));
                    
                },
                onError: (error) => {
                    setInvalid(true);
                    setTimeout(() => setInvalid(false), 2500);
                    console.error("Login failed: ", error);
                }
            }
        )
    }, [])

    return {
        handleLoginSubmit,
        isLoggingin: () => isLoading("logging-in")
    }
}