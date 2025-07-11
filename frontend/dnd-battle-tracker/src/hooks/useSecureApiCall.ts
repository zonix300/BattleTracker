import { useState, useCallback } from "react";
import { apiClient } from "../services/api";
import { getAuthToken, getCsrfToken } from "../util/auth";
import { clearAuthData, isTokenExpired } from "../components/Auth/Auth";

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
            skipAuth = false
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
                const token = getAuthToken();
                if (!token) {
                    throw new Error("No authentication token found");
                }

                if (isTokenExpired(token)) {
                    clearAuthData();
                    throw new Error("Token is expired");
                }

                headers["Authorization"] = `Bearer ${token}`;
            }

            const response = await apiClient[method](endpoint, data, {headers});

            if (onSuccess) {
                onSuccess(response.data)
            }

            return response.data;

        } catch (error: any) {
            console.error(`API call failed: ${method.toUpperCase()} ${endpoint}`, error);

            if (error.response?.status === 401) {
                clearAuthData();
                window.location.href = '/login';
                return;
            }

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