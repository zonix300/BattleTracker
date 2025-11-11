import React from "react";

type ConnectToastProps = {
    username: string;
    role: string;
    onAccept: () => void;
    onReject: () => void;
};

export const ConnectToast = ({ username, role, onAccept, onReject }: ConnectToastProps) => (
    <div>
        <div>{`${username} wants to join as ${role}`}</div>
        <button onClick={onAccept}>Accept</button>
        <button onClick={onReject}>Reject</button>
    </div>
);