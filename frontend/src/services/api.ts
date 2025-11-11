import axios from "axios";

const API_BASE_URL = process.env.REACT_APP_API_BASE_URL 

export const apiClient = axios.create({
    baseURL: API_BASE_URL,
    timeout: 10000,
    headers: {
        "Content-Type": "application/json",
    },
});