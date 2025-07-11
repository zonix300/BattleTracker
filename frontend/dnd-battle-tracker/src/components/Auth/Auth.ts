export const getAuthToken = (): string | null => {
    return localStorage.getItem("token");
};

export const isTokenExpired = (token: string): boolean => {
    try {
        const payload = JSON.parse(atob(token.split(".")[1]));
        return payload.exp * 1000 < Date.now();
    } catch {
        return true;
    }
};

export const clearAuthData = () => {
    localStorage.removeItem("token");
    localStorage.removeItem("user");
};