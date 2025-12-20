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
            <button
                onClick={handleClick}
                className="btn btn-gradient px-4 py-2 text-white fw-bold"
                style={{
                    background: 'linear-gradient(90deg, #6a11cb, #2575fc)',
                    border: 'none',
                    borderRadius: '50px',
                    boxShadow: '0 4px 15px rgba(0,0,0,0.2)',
                    transition: 'transform 0.2s, box-shadow 0.2s',
                }}
                onMouseEnter={(e) => {
                    e.currentTarget.style.transform = 'scale(1.05)';
                    e.currentTarget.style.boxShadow = '0 6px 20px rgba(0,0,0,0.3)';
                }}
                onMouseLeave={(e) => {
                    e.currentTarget.style.transform = 'scale(1)';
                    e.currentTarget.style.boxShadow = '0 4px 15px rgba(0,0,0,0.2)';
                }}
            >
                Login
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
