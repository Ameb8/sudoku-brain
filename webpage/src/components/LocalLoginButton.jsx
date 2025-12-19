import { useState, useEffect } from 'react';
import { Modal, Button } from 'react-bootstrap';
import LoginForm from '../forms/LoginForm';

const LocalLoginButton = () => {
    const [show, setShow] = useState(false);

    useEffect(() => {
        const close = () => setShow(false);
        window.addEventListener('close-login-modal', close);
        return () => window.removeEventListener('close-login-modal', close);
    }, []);

    return (
        <>
            {/* Sleek login button */}
            <Button
                variant="primary"
                className="px-4 py-2 rounded-pill fw-semibold shadow-sm"
                onClick={() => setShow(true)}
            >
                Login
            </Button>

            <Modal
                show={show}
                onHide={() => setShow(false)}
                centered
            >
                <Modal.Header closeButton>
                    <Modal.Title>Welcome Back</Modal.Title>
                </Modal.Header>

                <Modal.Body>
                    <LoginForm />
                </Modal.Body>
            </Modal>
        </>
    );
};

export default LocalLoginButton;
