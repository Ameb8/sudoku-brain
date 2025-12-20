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
                <Dropdown.Menu show className="show">
                    {user ? ( // Logged-in view
                        <>
                            <Dropdown.Item as={Link} to="/Profile">
                                Profile
                            </Dropdown.Item>
                            <Dropdown.Item href="#/settings">Settings</Dropdown.Item>
                            <Dropdown.Divider />
                            <Dropdown.Item onClick={onLogout}>Logout</Dropdown.Item>
                        </>
                    ) : ( // Logged-out view
                        <>
                            <Dropdown.Item as={Link} to="/login">
                                <LoginButton />
                            </Dropdown.Item>
                            <Dropdown.Item>
                                <LocalLoginButton />
                            </Dropdown.Item>
                            <Dropdown.Item>
                                <RegisterButton />
                            </Dropdown.Item>
                            <Dropdown.Item href="#/settings">Settings</Dropdown.Item>
                        </>
                    )}
                </Dropdown.Menu>
            </Dropdown>
        </>
    );
}










/*
import { Dropdown } from 'react-bootstrap';
import { Link } from 'react-router-dom';

export default function UserDropdown({ onLogout }) {
    return (
        <div className="user-dropdown shadow-sm">
            <Dropdown.Menu show className="show">
                <Dropdown.Item as={Link} to="/Profile">
                    Profile
                </Dropdown.Item>
                <Dropdown.Item href="#/settings">Settings</Dropdown.Item>
                <Dropdown.Divider />
                <Dropdown.Item onClick={onLogout}>Logout</Dropdown.Item>
            </Dropdown.Menu>
        </div>
    );
}

*/