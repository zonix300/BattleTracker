import React, { useState } from "react"
import { Login } from "./Login/Login"
import { Register } from "./Register/Register"
import { Modal } from "../../../hooks/useModal"

type AuthModalProps = {
    modal: Modal
}
export const AuthModal = ({modal}: AuthModalProps) => {
    const [isLoginView, setIsLoginView] = useState(true);
    if (isLoginView) {
        return <Login modal={modal} setIsLoginView={setIsLoginView}/>
    } else {
        return <Register modal={modal} setIsLoginView={setIsLoginView}/>
    }
}