import { useState } from "react";
import { useLoginSubmit } from "../../hooks/useLoginSubmit";
import { Credentials } from "../type/Credentials";

export const Login = () => {
    const [credentials, setCredentials] = useState<Credentials>({
        username: "",
        email: "",
        password: ""
    });

    const {
        handleLoginSubmit,
        isLoggingin
    } = useLoginSubmit();

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
            await handleLoginSubmit(credentials);
        } catch (error) {
            console.error(error);
        }
    }



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