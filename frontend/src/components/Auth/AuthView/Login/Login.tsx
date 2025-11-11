import { useState } from "react";
import { useLoginSubmit } from "../../../../hooks/useLoginSubmit";
import "../Auth.css";
import { useNavigate } from "react-router-dom";
import { Modal } from "../../../../hooks/useModal";

type Credentials = {
    identifier: string,
    password: string
}

type LoginProps = {
    modal: Modal,
    setIsLoginView: React.Dispatch<React.SetStateAction<boolean>>
}

export const Login = ({modal, setIsLoginView}: LoginProps) => {
    const [invalid, setInvalid] = useState(false);
    const [credentials, setCredentials] = useState<Credentials>({
        identifier: "",
        password: ""
    });

    const {
        handleLoginSubmit,
        isLoggingin
    } = useLoginSubmit(modal);

    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const { name, value } = e.target;
        setCredentials(prev => ({
            ...prev,
            [name]: value
        }));
    };

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();

        handleLoginSubmit(credentials, setInvalid);
        setCredentials({
            identifier: "",
            password: ""
        })
    };

    const handleRedirect = () => {
        setIsLoginView(false)
    }

    return (
        <form onSubmit={handleSubmit} className={invalid ? "auth_form invalid" : "auth_form"}>
            <div className="auth_form__container">
                <h2 className="auth_form__header">Login</h2>
                <input 
                    type="text"
                    name="identifier"
                    placeholder="username or email"
                    value={credentials.identifier}
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
                <a className="auth_form__redirect_container"><span 
                    className="auth_form__redirect" 
                    onClick={handleRedirect}
                >Don't have an account?</span></a>
            </div>
        </form>
    );
}