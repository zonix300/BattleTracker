import { useState, useEffect, ReactNode, MouseEvent } from "react";
import { ReactComponent as CloseButton } from "../../../icons/close-btn.svg"
import "./Modal.css"

interface useAnimatedModalProps {
  isOpen: boolean;
  onClose: () => void;
  children: ReactNode;
  title?: string;
  showCloseButton?: boolean;
  closeOnBackdropClick?: boolean;
}

export const useAnimatedModal = ({
    isOpen, 
    onClose, 
    children, 
    title, 
    showCloseButton = true, 
    closeOnBackdropClick = true
} : useAnimatedModalProps) => {
    const [isAnimating, setIsAnimating] = useState(true);

    useEffect(() => {
        if (isOpen) {
            setIsAnimating(true);
            document.body.style.overflow = "hidden";
        } else {
            const timer = setTimeout(() => setIsAnimating(false), 200);
            document.body.style.overflow = "unset";
            return () => clearTimeout(timer);
        }
    }, [isOpen]);

    const handleBackdropClick = (e : MouseEvent<HTMLDivElement>) => {
        if (closeOnBackdropClick && e.target === e.currentTarget) {
            onClose();
        }
    };

    if (!isOpen && !isAnimating) return null;

    return (
        <div className={`modal-overlay ${isOpen ? "open" : ""}`}>
            <div
                className="modal-backdrop"
                onClick={handleBackdropClick}
            />
            <div className={`modal-container ${isOpen ? "open" : ""}`}>
                <div className="modal-content">
                    {(title || showCloseButton) && (
                        <div className="modal-header">
                            {title && <h2 className="modal-title">{title}</h2>}
                            {showCloseButton && (
                                <button
                                    className="modal-close-button"
                                    onClick={onClose}
                                >
                                    <CloseButton className="modal-close-button-svg"/>
                                </button>
                            )}
                        </div>
                    )}
                    <div className="modal-body">{children}</div>
                </div>
            </div>
        </div>
    );


}