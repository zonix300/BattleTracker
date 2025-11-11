import { useCallback } from "react";
import { useSecureApiCall } from "./useSecureApiCall";

export const useGuestInit = (
) => {

    const {makeApiCall} = useSecureApiCall();

    const handleGuestInit = useCallback(async () => {
        await makeApiCall(
            "post",
            "api/auth/guest",
            {},
            {
                skipAuth: true,
                onSuccess: (data) => {
                    console.log(data.token);
                    localStorage.setItem("token", data.token);
                    localStorage.setItem("user", JSON.stringify(data.userInfo));
                }
            }
        )
    }, []);
    
    return handleGuestInit;
}