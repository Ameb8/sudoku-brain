import { useState } from 'react';

import LocalLoginForm from "../forms/LocalLoginForm.jsx";


const ToggleLoginButton = () => {
    const [showLogin, setShowLogin] = useState(false);

    const handleClick = () => {
        setShowLogin(true);
    };

    const handleClose = () => {
        setShowLogin(false);
    };

    return (
        <>
            <button onClick={handleClick} className="auth-btn auth-btn-primary">
                Log in with username
            </button>

            {showLogin && (
                <div
                    className="modal fade show d-block"
                    style={{ backgroundColor: 'rgba(0,0,0,0.5)' }}
                    onClick={handleClose} // close modal when clicking outside
                >
                    <div
                        className="modal-dialog modal-dialog-centered"
                        onClick={(e) => e.stopPropagation()} // prevent close on modal content click
                    >
                        <div className="modal-content">
                            <div className="modal-header">
                                <h5 className="modal-title">Login</h5>
                                <button
                                    type="button"
                                    className="btn-close"
                                    onClick={handleClose}
                                ></button>
                            </div>
                            <div className="modal-body">
                                <LocalLoginForm />
                            </div>
                        </div>
                    </div>
                </div>
            )}
        </>
    );
};

export default ToggleLoginButton;
