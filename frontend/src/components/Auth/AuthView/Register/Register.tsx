import { useState } from "react";
import { useRegisterSubmit } from "../../../../hooks/useRegisterSubmit";
import { Credentials } from "../../../type/Credentials";
import { Modal } from "../../../../hooks/useModal";
import "../Auth.css";

type RegisterProps = {
    modal: Modal,
    setIsLoginView: React.Dispatch<React.SetStateAction<boolean>>
}

export const Register = ({modal, setIsLoginView}: RegisterProps) => {

    const [credentials, setCredentials] = useState<Credentials>({
        username: "",
        email: "",
        password: ""
    });

    const {
        handleRegisterSubmit,
        isRegistering
    } = useRegisterSubmit(modal);

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

    const handleRedirect = () => {
        setIsLoginView(true);
    }

    return (
        <form onSubmit={handleSubmit} className="auth_form">
            <div className="auth_form__container">
                <h2 className="auth_form__header">Register</h2>
                <input
                    type="text"
                    name="username"
                    placeholder="username"
                    value={credentials.username}
                    onChange={handleChange}
                    className="auth_form__input"
                    required
                />
                <input
                    type="text"
                    name="email"
                    placeholder="email"
                    value={credentials.email}
                    onChange={handleChange}
                    className="auth_form__input"
                    required
                />
                <input
                    type="password"
                    name="password"
                    placeholder="password"
                    value={credentials.password}
                    onChange={handleChange}
                    className="auth_form__input"
                    required
                />
                <button
                    type="submit"
                    className="auth_form__submit_button"
                >Submit</button>
                <a className="auth_form__redirect_container">
                    <span onClick={handleRedirect} className="auth_form__redirect">Already have an account?</span>
                </a>
            </div>
        </form>
    );
}