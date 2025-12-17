import './UserAvatar.css';
import defaultAvatar from '../assets/default_avatar.png';
import { useUser } from "./UserProvider.jsx";
import { useState, useRef, useEffect } from "react";
import UserDropdown from './UserDropdown.jsx';

export default function UserAvatar() {
    const { user, loading } = useUser();
    const { setUser } = useUser();
    const [showDropdown, setShowDropdown] = useState(false);
    const avatarRef = useRef(null);

    const avatarUrl = user && user.profilePicture ? user.profilePicture : defaultAvatar;

    // Hide dropdown on outside click
    useEffect(() => {
        const handleClickOutside = (e) => {
            if (avatarRef.current && !avatarRef.current.contains(e.target)) {
                setShowDropdown(false);
            }
        };
        document.addEventListener("mousedown", handleClickOutside);
        return () => document.removeEventListener("mousedown", handleClickOutside);
    }, []);

    const handleLogout = () => {
        fetch("http://localhost:8080/logout", {
            method: "POST",
            credentials: "include"
        });

        setUser(null);
    };

    return (
        <div className="avatar-wrapper" ref={avatarRef}>
            {loading ? (
                <div>Loading...</div>
            ) : (
                <img
                    className="user-avatar"
                    src={avatarUrl}
                    alt={user ? user.username : "Guest"}
                    width="50"
                    height="50"
                    onClick={() => setShowDropdown((prev) => !prev)}
                />
            )}
            {showDropdown && (
                <div className="dropdown-container">
                    <UserDropdown onLogout={handleLogout} />
                </div>
            )}
        </div>
    );
}


/*
import './UserAvatar.css'
import defaultAvatar from '../assets/default_avatar.png'; // Adjust path accordingly
import {useUser} from "./UserProvider.jsx";

export default function UserAvatar() {
    const { user, loading } = useUser(); // Access user data and loading state from context

    console.log("User info:", user);

    // Determine the profile picture URL or fallback to the default image
    const avatarUrl = user && user.profilePicture ? user.profilePicture : defaultAvatar;

    return (
        <div className="avatar-container">
            {loading ? (
                <div>Loading...</div> // Loading state
            ) : (
                <img
                    className="user-avatar"
                    src={avatarUrl}
                    alt={user ? user.username : "Guest"}
                    width="50"
                    height="50"
                />
            )}
        </div>
    );
}
*/