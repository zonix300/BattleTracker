import { useState } from "react";
import { useRegisterSubmit } from "../../../hooks/useRegisterSubmit";
import { Credentials } from "../../type/Credentials";

export const Register = () => {
    const [credentials, setCredentials] = useState<Credentials>({
        username: "",
        email: "",
        password: ""
    });

    const {
        handleRegisterSubmit,
        isRegistering
    } = useRegisterSubmit();

    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const { name, value } = e.target;
        setCredentials(prev => ({
            ...prev,
            [name]: value
        }));
    };

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();

        try {
            await handleRegisterSubmit(credentials);
        } catch (error) {
            console.error(error);
        }
    };

    return (
        <form onSubmit={handleSubmit}>
            <div>
                <input
                    type="text"
                    name="username"
                    placeholder="username"
                    value={credentials.username}
                    onChange={handleChange}
                    required
                />
                <input
                    type="text"
                    name="email"
                    placeholder="email"
                    value={credentials.email}
                    onChange={handleChange}
                    required
                />
                <input
                    type="password"
                    name="password"
                    placeholder="password"
                    value={credentials.password}
                    onChange={handleChange}
                    required
                />
                <button
                    type="submit"
                >Submit</button>
            </div>
        </form>
    );
}