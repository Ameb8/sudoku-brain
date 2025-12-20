import { useState } from 'react';
import RegisterForm from '../forms/RegisterForm';

const ToggleRegisterButton = () => {
    const [showRegister, setShowRegister] = useState(false);

    const handleClick = () => {
        setShowRegister(true);
    };

    const handleClose = () => {
        setShowRegister(false);
    };

    return (
        <>
            <button onClick={handleClick} className="auth-btn auth-btn-secondary">
                Create account
            </button>

            {showRegister && (
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
                                <h5 className="modal-title">Register</h5>
                                <button
                                    type="button"
                                    className="btn-close"
                                    onClick={handleClose}
                                ></button>
                            </div>
                            <div className="modal-body">
                                <RegisterForm />
                            </div>
                        </div>
                    </div>
                </div>
            )}
        </>
    );
};

export default ToggleRegisterButton;
