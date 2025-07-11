import { all } from "axios";
import { ReactNode } from "react";
import { Navigate } from "react-router-dom";

export const ProtectedRoute = ({ children, allowedRoles = [] }: {children: ReactNode, allowedRoles?: string[]}) => {
    const token = localStorage.getItem("token");
    const user = JSON.parse(localStorage.getItem("user") || "{}");

    if (!token) {
        return <Navigate to={"/login"} replace />
    }

    if (allowedRoles.length > 0 && !allowedRoles.includes(user.role)) {
        return <Navigate to={"/unauthorized"} replace />
    }

    return <>{children}</>;
};