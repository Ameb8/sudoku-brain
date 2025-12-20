import "./Navbar.css";

import { Link } from "react-router-dom";

import UserProfile from "./UserProfile.jsx";
import UserAvatar from "./UserAvatar.jsx";
import LoginButton from "./LoginButton.jsx";
import { useUser} from "./UserProvider";

export default function Navbar() {
    const { user, loading } = useUser();

    if (loading) {
        return <div>Loading...</div>;
    }

    return (
        <nav className="navbar">
            <div className="navbar-title">
                <Link to="/">SudokuBrain</Link>
            </div>

            <div className="navbar-user">
                {user && <UserProfile />}
            </div>
            <div className="navbar-spacer-two"></div>
            <div>
                <UserAvatar />
            </div>
            <div className="navbar-spacer"></div>
        </nav>
    );
}



