import { Dropdown } from 'react-bootstrap';

export default function UserDropdown({ onLogout }) {
    return (
        <div className="user-dropdown shadow-sm">
            <Dropdown.Menu show className="show">
                <Dropdown.Item href="#/profile">Profile</Dropdown.Item>
                <Dropdown.Item href="#/settings">Settings</Dropdown.Item>
                <Dropdown.Divider />
                <Dropdown.Item onClick={onLogout}>Logout</Dropdown.Item>
            </Dropdown.Menu>
        </div>
    );
}