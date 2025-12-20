import { useState, useEffect } from 'react';
import { Dropdown } from 'react-bootstrap';
import { Link } from 'react-router-dom';
import { useUser } from './UserProvider';
import LoginButton from './LoginButton';
import RegisterButton from './RegisterButton';
import LocalLoginButton from "./LocalLoginButton.jsx";
import LocalLoginForm from "../forms/LocalLoginForm.jsx";

export default function UserDropdown({ onLogout }) {
    const { user } = useUser();
    const [showLogin, setShowLogin] = useState(false);
    const [showDropdown, setShowDropdown] = useState(true);

    return (
        <>
            <Dropdown show={showDropdown} autoClose={false}>
                <Dropdown.Menu className="user-dropdown-menu">
                    {user ? (
                        <>
                            <Dropdown.Item as={Link} to="/Profile">
                                Profile
                            </Dropdown.Item>
                            <Dropdown.Item href="#/settings">Settings</Dropdown.Item>
                            <Dropdown.Divider />
                            <Dropdown.Item onClick={onLogout}>Logout</Dropdown.Item>
                        </>
                    ) : (
                        <div className="auth-dropdown">
                            <LoginButton />

                            <div className="auth-divider">
                                <span>or</span>
                            </div>

                            <div className="auth-action">
                                <LocalLoginButton />
                            </div>

                            <div className="auth-action">
                                <RegisterButton />
                            </div>

                            <Dropdown.Divider />
                            <Dropdown.Item href="#/settings">Settings</Dropdown.Item>
                        </div>
                    )}
                </Dropdown.Menu>

            </Dropdown>
        </>
    );
}


