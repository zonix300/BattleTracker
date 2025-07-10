import { useState, useCallback } from "react";
import { apiClient } from "../services/api";
import { getAuthToken, getCsrfToken } from "../util/auth";

interface ApiCallOptions {
    showLoadingFor?: string;
    onSuccess?: (data: any) => void;
    onError?: (error: any) => void;
    validateInput?: (params: any) => boolean;
    skipAuth?: boolean;
}

export const useSecureApiCall = () => {
    const [loadingStates, setLoadingStates] = useState<Set<string>>(new Set());

    const makeApiCall = useCallback(async (
        method: "get" | "post" | "patch" | "put" | "delete",
        endpoint: string,
        data?: any,
        options: ApiCallOptions = {}
    ) => {
        const {
            showLoadingFor,
            onSuccess,
            onError,
            validateInput,
            skipAuth = true
        } = options;

        if (showLoadingFor && loadingStates.has(showLoadingFor)) {
            return;
        }

        try {
            if (validateInput && !validateInput(data)) {
                throw new Error("Invalid input data");
            }

            if (showLoadingFor) {
                setLoadingStates(prev => new Set(prev).add(showLoadingFor));
            }

            const headers: Record<string, string> = {};
            if (!skipAuth) {
                headers["Authorization"] = `Bearer ${getAuthToken()}`;
                //headers["X-CSRF-Token"] = getCsrfToken();
            }

            const response = await apiClient[method](endpoint, data, {headers});

            if (onSuccess) {
                onSuccess(response.data)
            }
        } catch (error) {
            console.error(`API call failed: ${method.toUpperCase()} ${endpoint}`);
            if (onError) {
                onError(error);
            }

            throw error;
        } finally {

            if (showLoadingFor) {
                setLoadingStates(prev => {
                    const newSet = new Set(prev);
                    newSet.delete(showLoadingFor);
                    return newSet;
                });
            }
        }
    }, [loadingStates]);

    return {
        makeApiCall,
        isLoading: (identifier: string) => loadingStates.has(identifier)
    };
};